package com.example.kotlin_todolist

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {
    private lateinit var todoInput: EditText
    private lateinit var todoLists: ListView
    private lateinit var todos: MutableList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Todo App"

        todoInput = findViewById(R.id.editText)
        val addButton: Button = findViewById(R.id.addTodoBtn)
        todoLists = findViewById(R.id.listView)

        todos = mutableListOf()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todos)
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
            (todoLists.adapter as ArrayAdapter<*>).notifyDataSetChanged()
        }
    }
}
