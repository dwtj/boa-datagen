package boa.util;

import com.google.common.truth.Truth;
import com.google.testing.compile.JavaFileObjects;
import com.google.testing.compile.JavaSourcesSubjectFactory;
import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.Trees;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

/**
 * @author dwtj
 */
public class CompilationUnitsProcessor {

    public static void fromResources(Consumer<CompilationUnitTree> fn, Collection<URL> urls) {
        Truth.assert_().about(JavaSourcesSubjectFactory.javaSources())
                .that(urls.stream().map(JavaFileObjects::forResource).collect(toList()))
                .processedWith(new Proc(fn))
                .compilesWithoutError();
    }

    public static void fromResourceNames(Consumer<CompilationUnitTree> fn, Collection<String> names) {
        Truth.assert_().about(JavaSourcesSubjectFactory.javaSources())
                .that(names.stream().map(JavaFileObjects::forResource).collect(toList()))
                .processedWith(new Proc(fn))
                .compilesWithoutError();
    }

    public static void fromJavaFileObjects(Consumer<CompilationUnitTree> fn, JavaFileObject... files) {
        assert files.length >= 1: "Must pass in at least one file to be processed.";
        fromJavaFileObjects(fn, asList(files));
    }

    public static void fromJavaFileObjects(Consumer<CompilationUnitTree> fn, Iterable<JavaFileObject> files) {
        Truth.assert_().about(JavaSourcesSubjectFactory.javaSources())
                .that(files)
                .processedWith(new Proc(fn))
                .compilesWithoutError();
    }


    @SupportedAnnotationTypes("*")
    @SupportedSourceVersion(SourceVersion.RELEASE_8)
    private static class Proc extends AbstractProcessor {

        final Consumer<CompilationUnitTree> consumer;

        public Proc(Consumer<CompilationUnitTree> consumer) {
            this.consumer = consumer;
        }

        @Override
        public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
            getCompilationUnitTrees(roundEnv).forEach(consumer::accept);
            return false;
        }

        List<CompilationUnitTree> getCompilationUnitTrees(RoundEnvironment roundEnv) {
            Trees treeUtils = Trees.instance(processingEnv);
            List<CompilationUnitTree> trees = new ArrayList<>();
            for (Element root : roundEnv.getRootElements()) {
                trees.add(treeUtils.getPath(root).getCompilationUnit());
            }
            return trees;
        }
    }
}
