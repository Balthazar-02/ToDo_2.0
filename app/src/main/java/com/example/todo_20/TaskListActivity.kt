package com.example.todo_20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class TaskListActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskIdemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        val onLongClickListener = object : TaskIdemAdapter.OnLongClickListener {
            override fun onItemLongClicked(position: Int) {
                // 1. remove the item to our list of tasks
                listOfTasks.removeAt(position)
                // 2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }


        }
        loadItems()


        // Look up the recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskIdemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Set a button and input field, so that the user can enter a task and add it in the list
        val inputTextField =  findViewById<EditText>( R.id.addTaskField)

        // Get a reference to the button
        // and set onClicklistener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. Grab the text that the user has imputed into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()


            // 2. Add the string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)

            // Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            // 3. Reset  the text field
            inputTextField.setText("")

            saveItems()
        }

    }


    // Save the data that the user has inputted
    // Save data by reading and writing from a file

    // Get the file we need
    fun getDataFile() : File {

        // Every line is going  to represent a specific task in our list of tasks
        return File(filesDir, "file.txt")
    }


    // Load the items by reading every line in our data file
    fun loadItems() {
        try {
            listOfTasks =
                org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())

        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }


        // Save items by writing them into our data file
        fun saveItems() {
            try {
                org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTasks)

            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }
        }
    }