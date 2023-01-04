package com.devfranz.mytasks

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.devfranz.mytasks.databinding.ResItemTaskBinding

class TaskAdapter(
    private val onClick: (Task) -> Unit,
    private val onDeleteClick: (Task) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    private val tasks = mutableListOf<Task>()

    inner class TaskViewHolder(
        itemView: ResItemTaskBinding
    ) : RecyclerView.ViewHolder(itemView.root) {

        private val tvTitleTask: TextView
        private val btnDelete: ImageButton
        private val clTask: ConstraintLayout


        init {
            tvTitleTask = itemView.tvTitleTask
            btnDelete = itemView.btnDelete
            clTask = itemView.clTask
        }

        fun bind(
            task: Task,
            onDeleteClick: (Task) -> Unit,
            onClick: (Task) -> Unit
        ) {
            tvTitleTask.text = task.title
            btnDelete.setOnClickListener {
                onDeleteClick(task)
            }
            clTask.setOnClickListener {
                onClick(task)
            }
            if (task.done) {
                clTask.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.success_green
                    )
                )
            } else {
                clTask.setBackgroundColor(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.dark_liver
                    )
                )
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            ResItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(
            tasks[position],
            onDeleteClick,
            onClick
        )
    }

    override fun getItemCount(): Int = tasks.size

    fun addTask(task: Task) {

        tasks.add(task)
        notifyItemInserted(tasks.size - 1)
    }

    fun deleteTask(task: Task) {
        val deletedPosition = tasks.indexOf(task)
        tasks.remove(task)
        notifyItemRemoved(deletedPosition)
    }

    fun updateTask(task: Task) {

        val updatePosition = tasks.indexOf(task)
        tasks[updatePosition] = task
        notifyItemChanged(updatePosition)

    }

    fun isEmpty() : Boolean = tasks.isEmpty()


}