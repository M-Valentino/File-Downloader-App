package com.example.astrobrowser

import android.app.DownloadManager
import android.content.*
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction


class MainActivity : AppCompatActivity() {
    // Holds String data of all downloaded items. Is passed through an intent to History activity.
    var itemData: String = ""
    var downloadID : Long = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val urlInput = findViewById<EditText>(R.id.textInputEditText)
        val fileNameInput = findViewById<EditText>(R.id.textInputEditText2)
        val downloadButton: Button = findViewById(R.id.downloadButton)

        val actionbar = supportActionBar
        actionbar!!.title = "File Downloader App"

        // After the download button is pressed, text from the input fields are used to download
        // a file. The keyboard is then hidden.
        downloadButton.setOnClickListener {
            hideKeyBoard()
            var request = DownloadManager.Request(Uri.parse(urlInput.text.toString()))
                .setTitle(fileNameInput.text.toString())
                .setDescription("Downloading...")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                //.setAllowedOverMetered(true)
                //.setAllowedOverRoaming(true)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, fileNameInput.text.toString())

            var dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadID = dm.enqueue(request)
        }

        var br = object:BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                var id = p1?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
                // If download has finished.
                if (id == downloadID) {
                    itemData += fileNameInput.text.toString() + "\n" + urlInput.text.toString() + "?"
                    // Brings up fragment indicating the file has been downloaded successfully
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.myFC, DownloadSuccessful())
                        setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        commit()
                    }
                }

            }
        }
        registerReceiver(br, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.htu ->
                supportFragmentManager.beginTransaction().apply {
                replace(R.id.myFC, HowToUse())
                setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                commit()
            }
            R.id.dh -> {
                goToHistory()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // This method is called after the download button is clicked.
    private fun hideKeyBoard() {
        val view = this.currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun goToHistory() {
        val intent = Intent(this, History::class.java)
        intent.putExtra("cardNum", itemData)
        if(intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }
}
