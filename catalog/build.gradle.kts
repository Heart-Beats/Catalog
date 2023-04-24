plugins {
	id("version-catalog")
	id("maven-publish")
}

apply {
	from("../maven_publish.gradle")
}

catalog {
	// 定义目录
	versionCatalog {
		val files = files(
			"/tomls/versionLibs.toml", "/tomls/pluginLibs.toml",
			"/tomls/dependendLibs.toml", "/tomls/bundleLibs.toml",
			"/tomls/compilerLibs.toml"
		)

		from(files)
	}
}

// project.publishing {
// 	val publish_groupId: String by extra
// 	println("publishing --------->${publish_groupId}")
// 	publications {
// 		this.register("versionCatalog", MavenPublication::class.java) {
// 			this.groupId = "com.hl"
// 			this.artifactId = "catalog"
// 			this.version = "1.0.0"
//
// 			this.from(components["versionCatalog"])
// 		}
// 	}
//
// 	this.repositories {
// 		// 配置 MavenLocal 仓库
// 		// mavenLocal()
// 	}
// }
