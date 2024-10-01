package com.example.christopherrojas_comp304sec003_lab01

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.christopherrojas_comp304sec003_lab01.ui.theme.ChristopherRojas_COMP304Sec003_Lab01Theme


class MainActivity : ComponentActivity() {
    private val notes = mutableStateListOf(
        Note(1, "Sample", "This is the content of the note."),
        Note(2, "Sample 2", "Another note to show.")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ChristopherRojas_COMP304Sec003_Lab01Theme {
                val navController = rememberNavController()
                AppNavHost(
                    navController = navController,
                    notes = notes,
                    addNote = { newNote ->
                        notes.add(newNote)
                    },
                    updateNote = { updatedNote ->
                        val index = notes.indexOfFirst { it.id == updatedNote.id }
                        if (index >= 0) {
                            notes[index] = updatedNote
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController, notes: List<Note>, addNote: (Note) -> Unit, updateNote: (Note) -> Unit) {
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController = navController, notes = notes) }
        composable("create_note") { CreateNoteScreen(navController = navController, addNote = addNote) }
        composable("view_edit_note/{noteId}") { backStackEntry ->
            val noteId = backStackEntry.arguments?.getString("noteId")?.toIntOrNull()
            val note = notes.find { it.id == noteId }
            if (note != null) {
                ViewEditNoteScreen(navController = navController, note = note, updateNote = updateNote)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateNoteScreen(navController: NavController, addNote: (Note) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Create Note") })
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )

                Button(onClick = {
                    if (title.isNotEmpty() && content.isNotEmpty()) {
                        val newNote = Note(
                            id = System.currentTimeMillis().toInt(), // Generate a unique ID for the note
                            title = title,
                            content = content
                        )
                        addNote(newNote)
                        navController.navigateUp()
                    }
                }) {
                    Text("Save")
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ViewEditNoteScreen(navController: NavController, note: Note, updateNote: (Note) -> Unit) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("View/Edit Note") })
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 5
                )

                Button(onClick = {
                    updateNote(note.copy(title = title, content = content))
                    navController.navigateUp()
                }) {
                    Text("Save")
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, notes: List<Note>) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("QuickNotes") })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("create_note")
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Add")
            }
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                modifier = Modifier.fillMaxSize().padding(innerPadding)
            ) {
                items(notes) { note ->
                    NoteItem(note = note, navController = navController)
                }
            }
        }
    )
}

@Composable
fun NoteItem(note: Note, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                navController.navigate("view_edit_note/${note.id}")
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = note.title, style = MaterialTheme.typography.titleMedium)
            Text(
                text = note.content,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
