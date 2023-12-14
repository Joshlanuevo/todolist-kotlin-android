package com.example.kotlin_todolist

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    private lateinit var todoInput: EditText
    private lateinit var todos: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Todo App"

        todoInput = findViewById(R.id.editText)
        val addButton: Button = findViewById(R.id.addTodoBtn)
        val editButton: Button = findViewById(R.id.editButton)
        val deleteButton: Button = findViewById(R.id.deleteButton)
        val todoLists: ListView = findViewById(R.id.listView)

        todos = mutableListOf()
        val adapter = object : ArrayAdapter<String>(
            this,
            R.layout.list_item_todo,
            R.id.todoItemText,
            todos
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)
                val editButton: Button = view.findViewById(R.id.editButton)
                val deleteButton: Button = view.findViewById(R.id.deleteButton)

                // Set visibility of buttons (always visible in this case)
                editButton.visibility = View.VISIBLE
                deleteButton.visibility = View.VISIBLE

                return view
            }
        }

        todoLists.adapter = adapter

        addButton.setOnClickListener {
            addTodo()
        }
    }

    private fun addTodo() {
        val todo = todoInput.text.toString()
        if (todo.isNotEmpty()) {
            todos.add(todo)
            todoInput.text.clear()
            (findViewById<ListView>(R.id.listView).adapter as ArrayAdapter<*>).notifyDataSetChanged()
        }
    }

    fun editTodo(view: View) {
        val position = findViewById<ListView>(R.id.listView).getPositionForView(view.parent as View)
        val todoToEdit = todos[position]

        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val dialogView: View = inflater.inflate(R.layout.edit_todo_dialog, null)
        val editText: EditText = dialogView.findViewById(R.id.editTextDialog)
        editText.setText(todoToEdit)

        val alertDialog = AlertDialog.Builder(this)
            .setTitle("Edit Todo")
            .setView(dialogView)
            .setPositiveButton("Save") { dialog, which ->
                // Update the todo item in the list
                todos[position] = editText.text.toString()

                // Notify the adapter that the data has changed
                (findViewById<ListView>(R.id.listView).adapter as ArrayAdapter<*>).notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .create()

        alertDialog.show()
    }

    fun deleteTodo(view: View) {
        val position = findViewById<ListView>(R.id.listView).getPositionForView(view.parent as View)
        todos.removeAt(position)
        (findViewById<ListView>(R.id.listView).adapter as ArrayAdapter<*>).notifyDataSetChanged()
    }
}
