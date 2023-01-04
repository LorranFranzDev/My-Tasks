package com.devfranz.mytasks

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.devfranz.mytasks.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdapter()
        setupLayout()


    }

    fun onDataUpdated() = if (adapter.isEmpty()) {
        binding.rvTasks.visibility = View.GONE
        binding.tvNoData.visibility = View.VISIBLE
    } else {
        binding.rvTasks.visibility = View.VISIBLE
        binding.tvNoData.visibility = View.GONE
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->

        if (result.resultCode != RESULT_OK)
            return@registerForActivityResult

        val task = result.data?.extras?.getSerializable("EXTRA_NEW_TASK") as Task
        adapter.addTask(task)
        onDataUpdated()

    }


    private fun setupLayout() {
        binding.fabNewTask.setOnClickListener {

            resultLauncher.launch(Intent(this, NewTaskActivity::class.java))


        }
    }

    private fun setupAdapter() {

        adapter = TaskAdapter(
            onDeleteClick = { taskToConfirmDeleted ->
                showDeleteConfirmation(taskToConfirmDeleted) { taskToBeDeleted ->
                    adapter.deleteTask(taskToBeDeleted)
                    onDataUpdated()
                }
            },
            onClick = { taskToBeShowed ->
                showTaskDetails(taskToBeShowed) { taskToBeUpdated ->
                    adapter.updateTask(taskToBeUpdated)
                }
            }
        )
        binding.rvTasks.adapter = adapter
        onDataUpdated()
    }

    private fun showTaskDetails(task: Task, onTaskStatusChanged: (Task) -> Unit) {

        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Detalhes da Tarefa")
            setMessage(
                """
                Título: ${task.title}
                Descrição: ${task.description}
                Concluída: ${
                    if (task.done)
                        "Sim"
                    else
                        "Não"
                }
                """.trimIndent()
            )
            setPositiveButton(
                if (task.done) "Não Concluída" else "Concluída"
            ) { _, _ ->
                task.done = !task.done
                onTaskStatusChanged(task)

            }
            setNegativeButton("Fechar") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()

    }

    private fun showDeleteConfirmation(task: Task, onConfirm: (Task) -> Unit) {

        val builder = AlertDialog.Builder(this)
        builder.apply {
            setTitle("Confirmação")
            setMessage("Deseja excluir a tarefa \"${task.title}\"?")
            setPositiveButton("Sim") { _, _ ->
                onConfirm(task)
                Toast.makeText(
                    this@MainActivity,
                    "Tarefa \"${task.title}\" excluida com sucesso",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            setNegativeButton("Não") { dialog, _ ->
                dialog.dismiss()
            }
        }
        builder.show()

    }


}

