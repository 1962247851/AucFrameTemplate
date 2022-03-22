package tech.ordinaryroad.android.lib.ui

import android.app.Activity
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.FileUtils
import android.provider.MediaStore
import androidx.core.net.toFile
import androidx.core.net.toUri
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.*
import es.dmoral.toasty.Toasty
import tech.ordinaryroad.android.lib.api.OrCallback
import tech.ordinaryroad.android.lib.util.OrFullCallback
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

/**
 * @author 19622
 */
object FileUtil {
    private val IMAGE_EXTENSIONS = listOf("jpg", "jpeg", "png", "gif", "bmp", "webp")
    private val AUDIO_EXTENSIONS =
        listOf("wav", "aif", "au", "mp3", "ram", "wma", "mmf", "amr", "aac", "flac", "pcm")
    private val VIDEO_EXTENSIONS = listOf(
        "mpeg",
        "mpg",
        "mp4",
        "m4v",
        "mov",
        "3gp",
        "3gpp",
        "3g2",
        "3gpp2",
        "mkv",
        "webm",
        "ts",
        "avi"
    )

    fun isImage(extensionWithoutDot: String?): Boolean {
        return extensionWithoutDot != null && IMAGE_EXTENSIONS.indexOf(extensionWithoutDot) != -1
    }

    fun isVideo(extensionWithoutDot: String?): Boolean {
        return extensionWithoutDot != null && VIDEO_EXTENSIONS.indexOf(extensionWithoutDot) != -1
    }

    fun isAudio(extensionWithoutDot: String?): Boolean {
        return extensionWithoutDot != null && AUDIO_EXTENSIONS.indexOf(extensionWithoutDot) != -1
    }

    fun getUri(
        context: Activity,
        filename: String,
        callback: OrCallback<Uri>,
        relativePath: String? = null
    ) {
        PermissionUtils.permissionGroup(PermissionConstants.STORAGE)
            .callback(OrPermissionCallback(object : OrFullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    ThreadUtils.executeByIo(object : ThreadUtils.SimpleTask<Uri>() {
                        override fun doInBackground(): Uri {
                            // Add a specific type item.
                            val resolver = context.contentResolver

                            val extension = filename.substringAfterLast(".")
                            val type = when {
                                isImage(extension) -> "图片"
                                isVideo(extension) -> "视频"
                                isAudio(extension) -> "音频"
                                else -> throw Exception("暂不支持的文件类型")
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                // Find all [type] files on the primary external storage device.
                                val collection = when (type) {
                                    "图片" -> {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                            MediaStore.Images.Media.getContentUri(
                                                MediaStore.VOLUME_EXTERNAL_PRIMARY
                                            )
                                        } else {
                                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                        }
                                    }
                                    "视频" -> {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                            MediaStore.Video.Media.getContentUri(
                                                MediaStore.VOLUME_EXTERNAL_PRIMARY
                                            )
                                        } else {
                                            MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                                        }
                                    }
                                    "音频" -> {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                            MediaStore.Audio.Media.getContentUri(
                                                MediaStore.VOLUME_EXTERNAL_PRIMARY
                                            )
                                        } else {
                                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                                        }
                                    }
                                    else -> throw Exception("暂不支持的文件类型")
                                }

                                // Publish
                                val details = when (type) {
                                    "图片" -> {
                                        ContentValues().apply {
                                            put(MediaStore.Images.Media.DISPLAY_NAME, filename)
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                                put(
                                                    MediaStore.Images.Media.RELATIVE_PATH,
                                                    "${Environment.DIRECTORY_PICTURES}/"
                                                            + context.getString(R.string.app_name)
                                                            + "/${relativePath ?: ""}"
                                                )
                                                put(MediaStore.Images.Media.IS_PENDING, 1)
                                            }
                                        }
                                    }
                                    "视频" -> {
                                        ContentValues().apply {
                                            put(MediaStore.Video.Media.DISPLAY_NAME, filename)
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                                put(
                                                    MediaStore.Video.Media.RELATIVE_PATH,
                                                    "${Environment.DIRECTORY_MOVIES}/"
                                                            + context.getString(R.string.app_name)
                                                            + "/${relativePath ?: ""}"
                                                )
                                                put(MediaStore.Video.Media.IS_PENDING, 1)
                                            }
                                        }
                                    }
                                    "音频" -> {
                                        ContentValues().apply {
                                            put(MediaStore.Audio.Media.DISPLAY_NAME, filename)
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                                put(
                                                    MediaStore.Audio.Media.RELATIVE_PATH,
                                                    "${Environment.DIRECTORY_MUSIC}/"
                                                            + context.getString(R.string.app_name)
                                                            + "/${relativePath ?: ""}"
                                                )
                                                put(MediaStore.Audio.Media.IS_PENDING, 1)
                                            }
                                        }
                                    }
                                    else -> throw Exception("暂不支持的文件类型")
                                }

                                // Keeps a handle to the new URI in case we need to modify it later.
                                return resolver.insert(collection, details)!!
                            } else {
                                val newFilePath = Environment.getExternalStoragePublicDirectory(
                                    when (type) {
                                        "图片" -> {
                                            Environment.DIRECTORY_PICTURES
                                        }
                                        "视频" -> {
                                            Environment.DIRECTORY_MOVIES
                                        }
                                        "音频" -> {
                                            Environment.DIRECTORY_MUSIC
                                        }
                                        else -> throw Exception("暂不支持的文件类型")
                                    }
                                ).path + "/" + context.getString(R.string.app_name) + "/" + (if (relativePath == null) "" else "${relativePath}/") + filename
                                return File(newFilePath).toUri()
                            }
                        }

                        override fun onSuccess(result: Uri?) {
                            if (result == null) {
                                callback.onFailure("失败")
                            } else {
                                callback.onResponse(result)
                            }
                        }
                    })
                }
            }))
            .request()
    }

    fun writeUriFromInputStream(context: Activity, uri: Uri, inputStream: InputStream) {
        PermissionUtils.permissionGroup(PermissionConstants.STORAGE)
            .callback(OrPermissionCallback(object : OrFullCallback {
                override fun onGranted(granted: MutableList<String>) {
                    ThreadUtils.executeByIo(
                        object : ThreadUtils.SimpleTask<Boolean>() {
                            override fun doInBackground(): Boolean {
                                val resolver = context.contentResolver
                                var success = false
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                                    // Write data into the pending file.
                                    resolver.openOutputStream(uri)?.use {
                                        success = try {
                                            FileUtils.copy(inputStream, it)
                                            true
                                        } catch (e: Exception) {
                                            LogUtils.e(e.cause, e.localizedMessage)
                                            false
                                        }
                                    }
                                    // Now that we're finished, release the "pending" status, and allow other apps to use.
                                    val details = ContentValues().apply {
                                        put(MediaStore.Audio.Media.IS_PENDING, 0)
                                    }
                                    resolver.update(uri, details, null, null)
                                } else {
                                    UriUtils.uri2File(uri)
                                    val newFile = uri.toFile()
                                    success = FileIOUtils.writeFileFromIS(newFile, inputStream)
                                }
                                return success
                            }

                            override fun onSuccess(result: Boolean?) {
                                if (result == true) {
                                    Toasty.success(context, R.string.file_save_successfully).show()
                                } else {
                                    Toasty.error(context, R.string.file_save_failed).show()
                                }
                            }
                        })
                }
            }))
            .request()
    }

    fun userUriOutputStream(context: Activity, uri: Uri, block: (OutputStream) -> Unit) {
        PermissionUtils.permissionGroup(PermissionConstants.STORAGE)
            .callback(object : PermissionUtils.SimpleCallback {
                override fun onGranted() {
                    val resolver = context.contentResolver
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // Write data into the pending file.
                        block(resolver.openOutputStream(uri)!!)
                        // Now that we're finished, release the "pending" status, and allow other apps to use.
                        val details = ContentValues().apply {
                            put(MediaStore.Audio.Media.IS_PENDING, 0)
                        }
                        resolver.update(uri, details, null, null)
                    } else {
                        UriUtils.uri2File(uri)
                        val newFile = uri.toFile()
                        com.blankj.utilcode.util.FileUtils.createOrExistsDir(newFile.parentFile)
                        block(FileOutputStream(newFile))
                    }
                }

                override fun onDenied() {
                    // TODO onDenied
                }
            })
            .request()
    }

    fun saveFile(
        context: Activity,
        filename: String,
        inputStream: InputStream,
        relativePath: String? = null
    ) {
        getUri(context, filename, object : OrCallback<Uri> {
            override fun onResponse(data: Uri) {
                writeUriFromInputStream(context, data, inputStream)
            }
        }, relativePath)
    }
}