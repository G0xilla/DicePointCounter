package xyz.urbysoft.dicepointcounter.mainactivity.ui

import androidx.activity.compose.BackHandler
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import xyz.urbysoft.dicepointcounter.R
import xyz.urbysoft.dicepointcounter.mainactivity.MainActivityViewModel
import xyz.urbysoft.dicepointcounter.pointcounter.Player
import xyz.urbysoft.dicepointcounter.pointcounter.getPoints
import xyz.urbysoft.dicepointcounter.ui.LocalLocale
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

@Preview(showBackground = true)
@Composable
fun NewPlayer(
    modifier: Modifier = Modifier,
    name: String = "",
    playerIndex: Int = 1,
    enableRemovePlayer: Boolean = true,
    showShortNameErrorMessage: Boolean = false,
    onNameChange: (String) -> Unit = { },
    onRemovePlayer: () -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.player, playerIndex + 1)) },
            trailingIcon = {
                if (enableRemovePlayer) {
                    IconButton(onClick = {
                        onRemovePlayer()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null
                        )
                    }
                }
            }
        )
        if (showShortNameErrorMessage) {
            ErrorTextLabel(text = stringResource(R.string.at_least_3_characters))
        }
    }
}

@Composable
fun ErrorTextLabel(
    modifier: Modifier = Modifier,
    text: String = ""
) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.labelSmall
    )
}

@Preview(showBackground = true)
@Composable
fun NewGameScreen(
    modifier: Modifier = Modifier,
    onNewGame: (List<String>) -> Unit = {}
) {
    var playerNames by rememberSaveable { mutableStateOf(listOf("", "")) }

    LazyColumn(modifier) {
        itemsIndexed(playerNames) { index, name ->
            NewPlayer(
                modifier = Modifier
                    .fillMaxWidth(),
                playerIndex = index,
                name = name,
                enableRemovePlayer = index > 1,
                showShortNameErrorMessage = name.trim().length < 3,
                onNameChange = {
                    val newList = playerNames.toMutableList()
                    newList.removeAt(index)
                    newList.add(index, it)
                    playerNames = newList
                },
                onRemovePlayer = {
                    val newList = playerNames.toMutableList()
                    newList.removeAt(index)
                    playerNames = newList
                }
            )
        }

        item {
            Row(
                modifier = Modifier
                    .padding(4.dp)
            ) {
                OutlinedButton(
                    onClick = {
                        val newList = playerNames.toMutableList()
                        newList.add("")
                        playerNames = newList
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = null
                    )
                    Text(stringResource(R.string.add_player))
                }

                Spacer(modifier.width(4.dp))

                Button(
                    onClick = {
                        onNewGame(playerNames.map { it.trim() })
                    },
                    modifier = Modifier.weight(1f),
                    enabled = playerNames.all { it.length >= 3 }
                ) {
                    Text(stringResource(R.string.start_game))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivePlayerPreview() {
    Column(
        modifier = Modifier
            .padding(4.dp)
    ) {
        ActivePlayer(player = Player("G0xilla", listOf(10.5)))
        ActivePlayer(player = Player("G0xilla", listOf(10.5)))
        ActivePlayer(player = Player("G0xilla", listOf(10.5)))
    }
}

@Composable
fun ActivePlayer(
    modifier: Modifier = Modifier,
    player: Player,
    onAddPoints: () -> Unit = {},
    onRevertPoints: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = player.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = if (player.getPoints() % 1 == 0.0) {
                        String.format(LocalLocale.current, "%.0f", player.getPoints())
                    } else {
                        val decimalFormat = DecimalFormat("0.###", DecimalFormatSymbols(LocalLocale.current))
                        val formattedNumber = decimalFormat.format(player.getPoints())
                        String.format(LocalLocale.current, "%s", formattedNumber)
                    },
                    style = MaterialTheme.typography.titleSmall
                )
            }

            IconButton(
                onClick = {
                    onAddPoints()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = null
                )
            }
            IconButton(
                onClick = {
                    onRevertPoints()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun ActivePlayerList(
    modifier: Modifier = Modifier,
    players: List<Player>,
    onAddPoints: (Player) -> Unit = {},
    onRevertPoints: (Player) -> Unit = {}
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(players) {
            ActivePlayer(
                player = it,
                onAddPoints = {
                    onAddPoints(it)
                },
                onRevertPoints = {
                    onRevertPoints(it)
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ActivePlayerScreenPreview() {
    ActivePlayerScreen(
        players = listOf(
            Player("G0xilla", listOf(10.5)),
            Player("Trenazery"),
            Player("GetRekt", listOf(20.0))
        ),
        modifier = Modifier.padding(4.dp)
    )
}

@Composable
fun ActivePlayerScreen(
    modifier: Modifier = Modifier,
    players: List<Player>,
    onAddPoints: (Player, Double) -> Unit = { _, _ -> },
    onRevertPoints: (Player) -> Unit = { }
) {
    var dialogPlayer by remember { mutableStateOf<Player?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    var showAddPointsDialog by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        ActivePlayerList(
            players = players,
            onAddPoints = {
                dialogPlayer = it
                showAddPointsDialog = true
                showDialog = true
            },
            onRevertPoints = {
                dialogPlayer = it
                showAddPointsDialog = false
                showDialog = true
            }
        )
        if (showDialog) {
            Dialog(
                onDismissRequest = {
                    showDialog = false
                    dialogPlayer = null
                }
            ) {
                Card {
                    if (showAddPointsDialog) {
                        Column(
                            Modifier.padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.add_points_for, dialogPlayer!!.name),
                                style = MaterialTheme.typography.titleLarge
                            )

                            var pointsText by remember { mutableStateOf("") }
                            OutlinedTextField(
                                label = {
                                    Text(text = stringResource(R.string.points))
                                },
                                value = pointsText,
                                onValueChange = { pointsText = it },
                                keyboardOptions = KeyboardOptions(
                                    keyboardType = KeyboardType.Number
                                )
                            )

                            if (pointsText.replace(',', '.').toDoubleOrNull() == null) {
                                ErrorTextLabel(text = stringResource(R.string.invalid_number))
                            }

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                        dialogPlayer = null
                                    },
                                    colors = ButtonDefaults
                                        .textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.secondary,
                                        )
                                ) {
                                    Text(stringResource(android.R.string.cancel))
                                }

                                TextButton(
                                    onClick = {
                                        val points = pointsText.replace(',', '.').toDoubleOrNull()
                                        if (points != null) {
                                            onAddPoints(dialogPlayer!!, points)
                                            showDialog = false
                                            dialogPlayer = null
                                        }
                                    },
                                    colors = ButtonDefaults
                                        .textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.primary
                                        )
                                ) {
                                    Text(stringResource(id = android.R.string.ok))
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            Text(
                                text = stringResource(
                                    R.string.revert_points_for,
                                    dialogPlayer!!.name
                                ),
                                style = MaterialTheme.typography.titleLarge
                            )

                            Row(
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                TextButton(
                                    onClick = {
                                        showDialog = false
                                        dialogPlayer = null
                                    },
                                    colors = ButtonDefaults
                                        .textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.secondary
                                        )
                                ) {
                                    Text(stringResource(id = android.R.string.cancel))
                                }

                                TextButton(
                                    onClick = {
                                        onRevertPoints(dialogPlayer!!)
                                        showDialog = false
                                        dialogPlayer = null
                                    },
                                    colors = ButtonDefaults
                                        .textButtonColors(
                                            contentColor = MaterialTheme.colorScheme.primary
                                        )
                                ) {
                                    Text(stringResource(id = android.R.string.ok))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun Screen(modifier: Modifier = Modifier, viewModel: MainActivityViewModel = viewModel()) {
    val playerListState by viewModel.playerList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        modifier = modifier
    ) { paddingValues ->
        if (playerListState != null) {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 4.dp)
                    .padding(top = 4.dp)
            ) {
                var showEndGameDialog by remember { mutableStateOf(false) }

                ActivePlayerScreen(
                    players = playerListState!!,
                    onAddPoints = { player, points ->
                        viewModel.addPoints(player, points)
                    },
                    onRevertPoints = {
                        try {
                            viewModel.revertPoints(it)
                        } catch (_: IllegalStateException) {
                        }
                    }
                )
                if (showEndGameDialog) {
                    Dialog(onDismissRequest = { showEndGameDialog = false }) {
                        Card {
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ) {
                                Text(stringResource(R.string.do_you_want_end_game))

                                Row(
                                    horizontalArrangement = Arrangement.End,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    TextButton(
                                        onClick = { showEndGameDialog = false }
                                    ) {
                                        Text(stringResource(id = android.R.string.cancel))
                                    }

                                    TextButton(
                                        onClick = {
                                            viewModel.resetGame()
                                        }
                                    ) {
                                        Text(stringResource(id = android.R.string.ok))
                                    }
                                }
                            }
                        }
                    }
                }

                BackHandler {
                    showEndGameDialog = true
                }
            }
        } else {
            NewGameScreen(
                onNewGame = {
                    viewModel.startNewGame(it)
                },
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(horizontal = 4.dp)
            )
        }
    }
}