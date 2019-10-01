import org.gradle.plugins.ide.idea.model.IdeaModel

subprojects {
    apply {
        plugin("java")
        plugin("idea")
    }

    afterEvaluate {
        repositories {
            jcenter()
        }

        configure<IdeaModel> {
            module {
                outputDir = File("$buildDir/classes/main")
                testOutputDir = File("$buildDir/classes/test")
            }
        }

        tasks.withType(JavaCompile::class.java) {
            doFirst {
                options.compilerArgs = listOf("--module-path", classpath.asPath)
            }

            sourceCompatibility = "11"
            targetCompatibility = "11"
        }
    }
}