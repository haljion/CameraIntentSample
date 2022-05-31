package com.websarva.wings.android.cameraintentsample

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    // 保存された画像のURI
    private var _imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // カメラアプリからの戻りでかつ撮影成功の場合
        if (requestCode == 200 && resultCode == RESULT_OK) {
            // 画像を表示するImageViewを取得
            val ivCamera = findViewById<ImageView>(R.id.ivCamera)
            // 撮影された画像をImageViewに設定
            ivCamera.setImageURI(_imageUri)
        }
    }

    fun onCameraImageClick(view: View) {
        // 日時データを「yyyyMMddHHmmss」の形式に整形するフォーマッタを生成
        val dateFormat = SimpleDateFormat("yyyyMMddHHmmss")
        // 現在日時を取得
        val now = Date()
        // 取得した日時データを「yyyyMMddHHmmss」の形式に整形した文字列を生成
        val nowStr = dateFormat.format(now)
        // ストレージに格納する画像のファイル名を生成
        val fileName = "CameraIntentSamplePhoto_${nowStr}.jpg"

        // ContentValuesオブジェクトを生成
        val values = ContentValues()
        // 画像ファイル名を設定
        values.put(MediaStore.Images.Media.TITLE, fileName)
        // 画像ファイルの種類を設定
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")

        // ContentResolverを使ってURIオブジェクトを生成
        _imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        // Intentオブジェクトを生成
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        // Extra情報として、_imageUriを設定
        intent.putExtra(MediaStore.EXTRA_OUTPUT, _imageUri)
        // アクティビティを起動
        startActivityForResult(intent, 200)
    }
}