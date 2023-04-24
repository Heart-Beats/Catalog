# Catalog

> Catalog 为 Gradle 7.0 以后针对 Android  项目推出的依赖统一管理的全新方式，具体相关使用博客可见：https://juejin.cn/post/6997396071055900680

目前本项目 Gradle 版本为 7.4.2，经过测试以后发现 7.4.2 之后的版本就无法按如下方式进行使用:

```kotlin
versionCatalog {
    val files = files("xxx.toml", "xxx.toml")

    from(files)
}
```

之后的版本 `files()` 里仅可传入一个文件，个人还是更倾向于多文件合成的方式，觉得这样分层会更清晰，如需升级需要修改对应部分。


在添加相关版本号时,还可以支持指定单个版本和版本范围,如:

```toml
commons-lang3 = { group = "org.apache.commons", name = "commons-lang3", version = { strictly = "[3.8, 4.0[", prefer="3.9" } }
```

可以参考: [Declaring Rich Versions](https://link.juejin.cn/?target=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fuserguide%2Frich_versions.html%23rich-version-constraints "https://docs.gradle.org/current/userguide/rich_versions.html#rich-version-constraints") 里官方的说明,这里简单说明下:

1. 版本范围用区间表示。`(`、`)` 表示开区间，`[`、`]` 表示闭区间。
   * `[` 放在区间右边相当于 `)`:  如 `[1, 2[` 相当于 `[1, 2)`
   * `]`放在区间左边相当于 `(`:   如 `]1, 2]` 相当于 `(1, 2]`
2. 有四种版本匹配模式可以指定：`strictly`、`require`、`prefer`、`reject`。
3. `require`（不指定模式时，`require` 是默认模式）的版本或版本范围对应的是 [VersionConstraint](https://link.juejin.cn?target=https%3A%2F%2Fdocs.gradle.org%2Fcurrent%2Fjavadoc%2Forg%2Fgradle%2Fapi%2Fartifacts%2FVersionConstraint.html "https://docs.gradle.org/current/javadoc/org/gradle/api/artifacts/VersionConstraint.html") 中的 `getRequiredVersion()`，required version 表示最低支持的版本，可以更高，但不能比它低。
4. 剩下三个皆为字面意思:
   * `strictly`: 表示严格要求的版本
   * `prefer`:   表示更偏爱优先选择的版本
   * `reject`:   表示拒绝接受的版本
