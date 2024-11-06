package com.example.rmaproject2.presentation.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.rmaproject2.R


class AddNoteActivity : AppCompatActivity() {

    private lateinit var title: String
    private lateinit var content: String
    private lateinit var id: String
    private lateinit var titleET: EditText
    private lateinit var contentET: EditText
    private lateinit var backBTn: Button
    private lateinit var saveBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        init()
    }

    private fun init(){
        initView()
        initListeners()
        if (intent.getStringExtra("type").toString() == "edit")
            initStrings()
    }

    private fun initStrings() {
        title = intent.getStringExtra("title").toString()
        content = intent.extras?.get("content").toString()
        id = intent.extras?.get("id").toString()

        titleET.setText(title)
        contentET.setText(content)
    }

    private fun initView() {
        titleET = findViewById<View>(R.id.noteTitleEdit) as EditText
        contentET = findViewById<View>(R.id.noteContentEdit) as EditText
        backBTn = findViewById<View>(R.id.backBtn) as Button
        saveBtn = findViewById<View>(R.id.editBtn) as Button
    }

    private fun initListeners() {
        backBTn.setOnClickListener {
            this.finish()
        }

        saveBtn.setOnClickListener {
            val returnIntent = Intent()
            returnIntent.putExtra("title", titleET.text.toString())
            returnIntent.putExtra("content", contentET.text.toString())
            if (intent.getStringExtra("type").toString() == "edit")
                returnIntent.putExtra("id", id)

            this.setResult(RESULT_OK, returnIntent)
            this.finish()
        }
    }

}