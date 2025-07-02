plugins {
	id("version-catalog")
}

// 定义合并 toml 文件的任务
tasks.register("mergeTomlFiles") {
	doLast {
		val inputFiles = listOf(
			file("tomls/versionLibs.toml"),
			file("tomls/pluginLibs.toml"),
			file("tomls/dependendLibs.toml"),
			file("tomls/bundleLibs.toml"),
		)
		val outputFile = rootProject.file("tomls/combinedLibs.toml")

		if (!outputFile.exists()) {
			// 确保创建的是文件的父目录，而不是文件本身
			outputFile.parentFile?.mkdirs()
		}

		// 清空输出文件
		outputFile.writeText("")

		inputFiles.forEachIndexed { index, inputFile ->
			if (inputFile.exists()) {
				if (index < inputFiles.size) {
					if (index > 0) {
						outputFile.appendText("\n\n")
					}

					outputFile.appendText("##################################  ${inputFile.nameWithoutExtension}   #####################################\n")
				}

				// 将每个文件的内容追加到输出文件
				outputFile.appendText(inputFile.readText())
				// 每个文件内容后添加一个空行分隔
				outputFile.appendText("\n")
			}
		}
	}
}

// 确保在生成目录前合并 toml 文件并处理合并后的文件
tasks.named("generateCatalogAsToml") {
	dependsOn("mergeTomlFiles")
}

catalog {
	// 定义目录
	versionCatalog {
		// 确保文件存在且路径正确
		val combinedFile = rootProject.file("tomls/combinedLibs.toml")
		if (combinedFile.exists()) {
			// 直接传入 File 对象
			from(files(combinedFile))
		} else {
			logger.warn("Combined toml file does not exist: ${combinedFile.absolutePath}")
		}
	}
}