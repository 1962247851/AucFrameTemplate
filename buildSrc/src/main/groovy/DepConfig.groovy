/**
 * <pre>
 *     author: blankj
 *     blog  : http://blankj.com
 *     time  : 2019/07/13
 *     desc  :
 * </pre>
 */
class DepConfig {
    /**
     * 是否应用
     */
    boolean isApply
    /**
     * 是否使用本地的
     */
    boolean useLocal
    /**
     * 本地路径
     */
    String localPath
    /**
     * 远程路径
     */
    String remotePath
    /**
     * 插件路径
     */
    String pluginPath
    /**
     * 插件 ID
     */
    String pluginId
    /**
     * 根据条件生成项目最终的依赖项
     */
    def dep

    DepConfig() {
        isApply = true
    }

    DepConfig(String path) {
        this(true, path)
    }

    DepConfig(boolean isApply, String path) {
        if (path.startsWith(":")) {
            this.useLocal = true
            this.localPath = path
            this.isApply = isApply
        } else {
            this.useLocal = false
            this.remotePath = path
            this.isApply = isApply
        }
    }

    DepConfig(boolean useLocal, String localPath, String remotePath) {
        this(true, useLocal, localPath, remotePath)
    }

    DepConfig(boolean isApply, boolean useLocal, String localPath) {
        this(isApply, useLocal, localPath, null)
    }

    DepConfig(boolean isApply, boolean useLocal, String localPath, String remotePath) {
        this.isApply = isApply
        this.useLocal = useLocal
        this.localPath = localPath
        this.remotePath = remotePath
    }

    String getPath() {
        if (pluginPath != null) return pluginPath
        return useLocal ? localPath : remotePath
    }

    String getGroupId() {
        String[] splits = remotePath.split(":")
        return splits.length == 3 ? splits[0] : null
    }

    String getArtifactId() {
        String[] splits = remotePath.split(":")
        return splits.length == 3 ? splits[1] : null
    }

    String getVersion() {
        String[] splits = remotePath.split(":")
        return splits.length == 3 ? splits[2] : null
    }

    String getProjectPath() {
        return localPath.substring(0, localPath.lastIndexOf(":")) + ":" + localPath.substring(1).replace(":", "_")
    }

    @Override
    String toString() {
        return "{ isApply = ${getFlag(isApply)}" +
                ", useLocal = ${getFlag(useLocal)}" +
                (dep == null ? ", path = " + path : (", dep = " + dep)) +
                " }"
    }

    static String getFlag(boolean b) {
        return b ? "✅" : "❌"
    }
}