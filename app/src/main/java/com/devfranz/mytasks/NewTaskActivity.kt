package com.devfranz.mytasks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.devfranz.mytasks.databinding.ActivityNewTaskBinding

class NewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {

            onSubmit()
            Toast.makeText(this@NewTaskActivity, "Tarefa adicionada com sucesso", Toast.LENGTH_SHORT)
                .show()

        }
    }

    private fun onSubmit() {

        if (binding.edtTaskTitle.text.isEmpty()) {
            binding.edtTaskTitle.error = "Por favor, preencha o título da tarefa"
            binding.edtTaskTitle.requestFocus()
            return
        }

        if (binding.edtDescriptionTask.text.isEmpty()) {
            binding.edtDescriptionTask.error = "Por favor, preencha a descrição da tarefa"
            binding.edtDescriptionTask.requestFocus()
            return
        }

        val newTask = Task(
            binding.edtTaskTitle.text.toString(),
            binding.edtDescriptionTask.text.toString()
        )

        val intentResult = Intent()
        intentResult.putExtra("EXTRA_NEW_TASK", newTask)
        setResult(RESULT_OK, intentResult)
        finish()
    }
}