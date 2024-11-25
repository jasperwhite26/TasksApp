package com.example.tasksapp

import android.os.Bundle
import android.widget.NumberPicker.OnValueChangeListener
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.arch.core.internal.SafeIterableMap.SupportRemove
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tasksapp.ui.theme.TasksAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TasksAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TaskApp(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TaskApp(name: String, modifier: Modifier = Modifier) {
    var tasks by remember { mutableStateOf(listOf<String>()) }
    var currentTask by remember { mutableStateOf(TextFieldValue("")) }

    Column (
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .height(500.dp)
            .width(300.dp)
    ){
        Text(
            text = stringResource(R.string.to_do_list),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column {
            for(task in tasks){
                TaskItem(task) {
                    tasks = tasks.filterNot { it == task }
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        TaskInput(currentTask, onTaskAdded = {newTask ->
            if (newTask.isNotBlank()){
                tasks = tasks + newTask
                currentTask = TextFieldValue("")
            }
        })
    }
}


@Composable
fun TaskItem(task: String, onRemove: () -> Unit) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            task,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = onRemove) {
            Icon(Icons.Default.Delete, contentDescription = "Remove Task")
        }
    }

}

@Composable
fun TaskInput(
    currentTask: TextFieldValue,
    onTaskAdded: (String) -> Unit,
    modifier: Modifier = Modifier
){

    TextField(
        value = currentTask,
        onValueChange = { newValue -> currentTask = newValue},
        label = { Text(stringResource(R.string.input_text))},
        singleLine = true,
        keyboardOptions = KeyboardOptions(),
        modifier = modifier
    )
    Spacer(modifier = Modifier.width(8.dp))

    Button(onClick = { onTaskAdded(currentTask.text) }) {
        Text("Add")

    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TasksAppTheme {
        TaskApp("Android")
    }
}