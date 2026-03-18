package com.example.legacy.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.legacy.R

/**
 * Demonstrates three Jetpack Compose interop scenarios:
 *
 *  1. Full Compose screen  — entire UI declared as @Composable functions.
 *  2. State in Compose     — remember { mutableStateOf() } drives recomposition.
 *  3. AndroidView          — embedding a legacy View inside a Compose tree.
 */
class ComposeDemoActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent replaces setContentView.
        // Everything inside the lambda is the Compose UI tree.
        setContent {
            MaterialTheme {
                ComposeDemo()
            }
        }
    }
}

// ─── Root screen ────────────────────────────────────────────────────────────

@Composable
private fun ComposeDemo() {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabs = listOf("State & Lists", "AndroidView interop")

    Scaffold(
        topBar = {
            // Compose equivalent of the XML Toolbar
            @OptIn(ExperimentalMaterial3Api::class)
            TopAppBar(title = { Text("Jetpack Compose Demo") })
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            PrimaryTabRow(selectedTabIndex = selectedTab) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick  = { selectedTab = index },
                        text     = { Text(title) }
                    )
                }
            }

            when (selectedTab) {
                0 -> StateAndListsTab()
                1 -> AndroidViewInteropTab()
            }
        }
    }
}

// ─── Tab 1 — State & Lists ───────────────────────────────────────────────────

@Composable
private fun StateAndListsTab() {
    // remember keeps the value alive across recompositions.
    // mutableIntStateOf triggers recomposition when the value changes.
    var counter by remember { mutableIntStateOf(0) }
    var items   by remember { mutableStateOf(listOf("Kotlin", "Compose", "Material 3")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Counter section
        SectionLabel("State — remember { mutableStateOf() }")
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(onClick = { counter-- }) { Text("−") }
            Text("$counter", fontSize = 24.sp)
            Button(onClick = { counter++ }) { Text("+") }
        }

        HorizontalDivider()

        // LazyColumn section (Compose RecyclerView equivalent)
        SectionLabel("LazyColumn — only visible items are composed")
        Button(onClick = { items = items + "Item ${items.size + 1}" }) {
            Text("Add item")
        }
        LazyColumn(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            items(items) { item ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors   = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                ) {
                    Text(
                        text     = item,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
                    )
                }
            }
        }
    }
}

// ─── Tab 2 — AndroidView interop ────────────────────────────────────────────

@Composable
private fun AndroidViewInteropTab() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        SectionLabel("AndroidView — legacy View inside Compose")
        Text(
            "Use AndroidView to embed any existing View (e.g. MapView, " +
            "WebView, custom views) directly inside a Compose tree.",
            style = MaterialTheme.typography.bodyMedium
        )

        // AndroidView is the bridge: Compose manages the lifecycle,
        // factory creates the View, update runs on every recomposition.
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFE3F2FD))
                .padding(12.dp),
            factory = { context ->
                // Create any legacy View here
                TextView(context).apply {
                    text    = "I am a legacy TextView living inside a Compose tree.\n\n" +
                              "factory {} — called once to create the view.\n" +
                              "update {}  — called on every recomposition."
                    textSize = 14f
                    setPadding(8, 8, 8, 8)
                }
            },
            update = { view ->
                // Called on recomposition — update the view with new state here
                view.setTextColor(android.graphics.Color.parseColor("#0D47A1"))
            }
        )

        HorizontalDivider()

        SectionLabel("ComposeView — opposite direction")
        Text(
            "To embed Compose inside an XML layout, add a ComposeView to " +
            "your XML and call view.setContent { } in the Activity/Fragment. " +
            "See activity_compose_interop.xml for an example.",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

// ─── Helpers ─────────────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text  = text,
        style = MaterialTheme.typography.labelMedium,
        color = MaterialTheme.colorScheme.primary
    )
}
