package com.capstone.peradaban.singkat.ajukan

import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.capstone.peradaban.singkat.R
import com.capstone.peradaban.singkat.status.DetailStatusActivity
import com.capstone.peradaban.singkat.ui.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import okhttp3.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.util.concurrent.TimeUnit

class UploadFotoActivity : AppCompatActivity() {
    var selectedImagePath: String? = null
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_foto)
        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser

        if(currentUser==null){
            startActivity(Intent(this, PhoneAuthActivity::class.java))
            finish()
        }


    }

    fun connectServer(v: View?) {
        val postUrl = "http://35.224.144.132:5000/"
        val stream = ByteArrayOutputStream()
        val options = BitmapFactory.Options()
        options.inPreferredConfig = Bitmap.Config.RGB_565
        // Read BitMap by file path
        val bitmap = BitmapFactory.decodeFile(selectedImagePath, options)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        val postBodyImage: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart(
                "image",
                "androidFlask.jpg",
                RequestBody.create(MediaType.parse("image/*jpg"), byteArray)
            )
            .build()
        val responseText = findViewById<TextView>(R.id.responseText)
        responseText.text = "Please wait ..."
        Toast.makeText(this,"Mohon Tunggu Sampai Selesai", Toast.LENGTH_SHORT).show()
        postRequest(postUrl, postBodyImage)
//        logout()
    }


    fun postRequest(postUrl: String?, postBody: RequestBody?) {
        val client = OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()
        val request = Request.Builder()
            .url(postUrl)
            .post(postBody)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                // Cancel the post on failure.
                call.cancel()

                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread {
                    val responseText = findViewById<TextView>(R.id.responseText)
                    responseText.text = "Failed to Connect to Server"
                    Log.d("MAIN_ES", "$e")
                }
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                // In order to access the TextView inside the UI thread, the code is executed inside runOnUiThread()
                runOnUiThread {
                    val responseText = findViewById<TextView>(R.id.responseText)
                    try {
//
                        responseText.text = response.body().string()
//
                        Log.d("MAIN_ES", "${responseText.text}")
                        updateData(responseText.text.toString())
//                        startActivity(Intent(this@UploadFotoActivity, KonfirmasiFormActivity::class.java))
//                        finish()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }
            }
        })
    }

    fun selectImage(v: View?) {
        val intent = Intent()
        intent.type = "*/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, 0)
    }



    override fun onActivityResult(reqCode: Int, resCode: Int, data: Intent?) {
        super.onActivityResult(reqCode, resCode, data)
        if (resCode == RESULT_OK && data != null) {
            val uri = data.data
            selectedImagePath = getPath(applicationContext, uri)
            val imgPath = findViewById<EditText>(R.id.imgPath)
            imgPath.setText(selectedImagePath)


        }
    }


    companion object {
            const val KEY = "NIK_KEY"
            const val IMAGE_PICK_CODE = 999
        // Implementation of the getPath() method and all its requirements is taken from the StackOverflow Paul Burke's answer: https://stackoverflow.com/a/20559175/5426539
        fun getPath(context: Context, uri: Uri?): String? {
            val isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    if ("primary".equals(type, ignoreCase = true)) {
                        return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
                    }

                    // TODO handle non-primary volumes
                } else if (isDownloadsDocument(uri)) {
                    val id = DocumentsContract.getDocumentId(uri)
                    val contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        java.lang.Long.valueOf(id)
                    )
                    return getDataColumn(context, contentUri, null, null)
                } else if (isMediaDocument(uri)) {
                    val docId = DocumentsContract.getDocumentId(uri)
                    val split = docId.split(":".toRegex()).toTypedArray()
                    val type = split[0]
                    var contentUri: Uri? = null
                    if ("image" == type) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    } else if ("video" == type) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    } else if ("audio" == type) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                    val selection = "_id=?"
                    val selectionArgs = arrayOf(
                        split[1]
                    )
                    return getDataColumn(context, contentUri, selection, selectionArgs)
                }
            } else if ("content".equals(uri!!.scheme, ignoreCase = true)) {
                return getDataColumn(context, uri, null, null)
            } else if ("file".equals(uri.scheme, ignoreCase = true)) {
                return uri.path
            }
            return null
        }

        fun getDataColumn(
            context: Context, uri: Uri?, selection: String?,
            selectionArgs: Array<String>?
        ): String? {
            var cursor: Cursor? = null
            val column = "_data"
            val projection = arrayOf(
                column
            )
            try {
                cursor = context.contentResolver.query(
                    uri!!, projection, selection, selectionArgs,
                    null
                )
                if (cursor != null && cursor.moveToFirst()) {
                    val column_index = cursor.getColumnIndexOrThrow(column)
                    return cursor.getString(column_index)
                }
            } finally {
                cursor?.close()
            }
            return null
        }


        fun isExternalStorageDocument(uri: Uri?): Boolean {
            return "com.android.externalstorage.documents" == uri!!.authority
        }

        fun isDownloadsDocument(uri: Uri?): Boolean {
            return "com.android.providers.downloads.documents" == uri!!.authority
        }

        fun isMediaDocument(uri: Uri?): Boolean {
            return "com.android.providers.media.documents" == uri!!.authority
        }
    }
    fun updateData(status: String){
        val extras = intent.extras

        if(extras != null) {
            val nik = extras.getString(DetailStatusActivity.KEY)
            Log.d("MAIN_ES", "$nik")
            if (nik != null) {
                Log.d("MAIN_ES", "$nik")
                database = FirebaseDatabase.getInstance().getReference("Users")
                val user = mapOf<String,String>(
                        "resultModel" to status,

                        )
                database.child(nik).updateChildren(user).addOnCompleteListener {
                    Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                    startActivity(Intent(this@UploadFotoActivity, KonfirmasiFormActivity::class.java))
                    finish()

                }
            }
        }
    }
     fun launchGallery(v: View?) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }
}
