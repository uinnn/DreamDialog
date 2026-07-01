plugins {
  kotlin("jvm") version "2.4.0"
  id("java")
  id("com.vanniktech.maven.publish") version "0.36.0"
  id("io.papermc.paperweight.userdev") version "2.0.0-beta.21"
}

group = "io.github.vernearth"
val artifact = "dream-dialog"
version = "1.0.0"

repositories {
  mavenCentral()
}

dependencies {
  paperweight.paperDevBundle("1.21.11-R0.1-SNAPSHOT")
}

mavenPublishing {
  publishToMavenCentral(true)
  signAllPublications()

  coordinates(group.toString(), artifact, version.toString())

  pom {
    name.set(artifact)
    description.set(artifact)
    url.set("https://github.com/uinnn")

    licenses {
      license {
        name.set("MIT License")
        url.set("https://opensource.org/licenses/MIT")
      }
    }

    developers {
      developer {
        id.set("carrara")
        name.set("José Carrara")
      }
    }

    scm {
      url.set("https://github.com/uinnn")
    }
  }
}
