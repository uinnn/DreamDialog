# DreamDialog
A powerful Kotlin DSL library for creating interactive dialogs in Minecraft Paper/Spigot plugins. DreamDialog provides an intuitive API for building confirmation dialogs, multi-action dialogs, notice dialogs, and more with support for various input types and callbacks.


## Features

- **Multiple Dialog Types**: Confirmation, Multi-Action, Notice, Dialog List, and Server Links dialogs
- **Rich Input Support**: Boolean, Text, Number Range, and Single Option inputs
- **Type-Safe Callbacks**: Handle user input with type-safe callbacks
- **Property Reflection**: Automatically bind inputs to properties
- **DSL Builder Pattern**: Clean and intuitive Kotlin DSL for dialog construction
- **Lifecycle Hooks**: Open, close, tick, and click event handlers
- **Sync/Async Support**: Control dialog execution mode
- **MiniMessage Support**: Built-in Adventure API text formatting

## Installation

Add the dependency to your `build.gradle.kts`:

```kotlin
repositories {
  mavenCentral()
}

dependencies {
  implementation("io.github.vernearth:dream-dialog:1.0.0")
}
```

# How DreamDialog is made
Dream Dialog is a super simple yet powerful library for creating dialogs; it’s basically an NMS wrapper for a real dialog, containing a mix of NMS, Bukkit, and Adventure code snippets.

Callbacks (clicks on the dialog) are handled by a specific Netty handler that we add to and remove from a player’s channel every time they open or close the dialog.

## Notes:
- I don't recommend setting `canCloseWithEscape = true`, since this doesn't trigger the close callbacks correctly
- sync/async applies only to clicks; tick callbacks are always asynchronous.
- If you don't want to trigger the input callbacks when clicking buttons, set `callInputs = false`. This should always be used whenever you want to close the dialog as if it had been canceled.

## Quick Start

### Basic Confirmation Dialog

```kotlin
player.openConfirmationDialog {
    common(title = "Confirm Action", canCloseWithEscape = false) {
        body {
            text("Are you sure you want to proceed?")
        }
    }

    yesButton("confirm", "Confirm") {
        player.sendMessage("Action confirmed!")
    }

    noButton("cancel", "Cancel") {
        player.sendMessage("Action cancelled")
    }
}
```

## Dialog Types

### 1. Confirmation Dialog

A dialog with two buttons (Yes/No) for binary choices.

```kotlin
player.openConfirmationDialog {
    common(title = "Delete Item", canCloseWithEscape = false) {
        body {
            text("This action cannot be undone.")
            item(ItemStack.of(Material.DIAMOND_SWORD), "Legendary Sword")
        }
    }

    yesButton("delete", "Delete") {
        player.sendMessage("Item deleted")
    }

    noButton("keep", "Keep") {
        player.sendMessage("Item kept")
    }
}
```

### 2. Multi-Action Dialog

A dialog with multiple custom action buttons.

```kotlin
player.openMultiActionDialog {
    common(title = "Select Action", canCloseWithEscape = false) {
        body {
            text("Choose an action to perform")
        }
    }

    add("teleport", "Teleport to Spawn") {
        player.teleport(player.world.spawnLocation)
    }

    add("heal", "Heal Yourself") {
        player.health = player.maxHealth
    }

    add("feed", "Feed Yourself") {
        player.foodLevel = 20
    }

    exitButton("close", "Close")
}
```

### 3. Notice Dialog

A simple dialog with a single action button.

```kotlin
player.openNoticeDialog {
    common(title = "Welcome!", canCloseWithEscape = false) {
        body {
            text("Welcome to our server!")
            text("Enjoy your stay!")
        }
    }

    button("continue", "Continue") {
        player.sendMessage("Let's get started!")
    }
}
```

### 4. Dialog List Dialog

A dialog that displays a list of other dialogs.

```kotlin
val dialog1 = buildConfirmationDialog {
    common(title = "Dialog 1") {
        body { text("First dialog") }
    }
    yesButton("yes", "Yes") {}
    noButton("no", "No") {}
}

val dialog2 = buildNoticeDialog {
    common(title = "Dialog 2") {
        body { text("Second dialog") }
    }
    button("ok", "OK") {}
}

player.openDialogListDialog {
    common(title = "Select Dialog") {
        body { text("Choose a dialog to open") }
    }
    
    dialogs(HolderSet.direct(dialog1.wrapper, dialog2.wrapper))
    buttonWidth(200)
    columns(2)
    
    exitButton("exit", "Exit")
}
```

### 5. Server Links Dialog

A dialog for displaying server links (Discord, website, etc.).

```kotlin
player.openServerLinksDialog {
    common(title = "Server Links") {
        body {
            text("Join our community!")
        }
    }
    
    columns(1)
    buttonWidth(180)
    
    exitButton("close", "Close")
}
```

## Input Types

### Boolean Input

```kotlin
player.openConfirmationDialog {
    common(title = "Settings") {
        body {
            text("Configure your preferences")
        }
        
        inputs {
            boolean("pvp", "Enable PvP", initial = true)
            boolean("notifications", "Enable Notifications", initial = false)
        }
    }

    yesButton("save", "Save") {
        player.sendMessage("Settings saved")
    }

    noButton("cancel", "Cancel") {}
}
```

### Text Input

```kotlin
player.openConfirmationDialog {
    common(title = "Enter Name") {
        body {
            text("Please enter your display name")
        }
        
        inputs {
            text("name", "Display Name", initial = player.name, maxLength = 16)
        }
    }

    yesButton("submit", "Submit") {
        player.sendMessage("Name submitted")
    }

    noButton("cancel", "Cancel") {}
}
```

### Multiline Text Input

```kotlin
player.openConfirmationDialog {
    common(title = "Write Message") {
        body {
            text("Enter your message below")
        }
        
        inputs {
            text(
                key = "message",
                label = "Message",
                initial = "",
                maxLength = 256,
                maxLines = 5,
                height = 80
            )
        }
    }

    yesButton("send", "Send") {
        player.sendMessage("Message sent")
    }

    noButton("cancel", "Cancel") {}
}
```

### Number Range Input

```kotlin
player.openConfirmationDialog {
    common(title = "Set Volume") {
        body {
            text("Adjust the volume level")
        }
        
        inputs {
            numberRange("volume", "Volume", range = 0f..100f, initial = 50f, step = 5f)
        }
    }

    yesButton("apply", "Apply") {
        player.sendMessage("Volume applied")
    }

    noButton("cancel", "Cancel") {}
}
```

### Integer Range Input

```kotlin
player.openConfirmationDialog {
    common(title = "Select Amount") {
        body {
            text("How many items?")
        }
        
        inputs {
            numberRange("amount", "Amount", range = 1..64, initial = 1, step = 1)
        }
    }

    yesButton("confirm", "Confirm") {
        player.sendMessage("Amount confirmed")
    }

    noButton("cancel", "Cancel") {}
}
```

### Single Option Input

```kotlin
player.openConfirmationDialog {
    common(title = "Select Difficulty") {
        body {
            text("Choose your difficulty")
        }
        
        inputs {
            options("difficulty", "Difficulty") {
                call { value ->
                    player.sendMessage("Difficulty set to $value")
                }
              
                add("easy", "Easy")
                add("normal", "Normal")
                add("hard", "Hard")
            }
        }
    }

    yesButton("start", "Start") {
        player.sendMessage("Difficulty set")
    }

    noButton("cancel", "Cancel") {}
}
```

## Advanced Usage

### Input Callbacks

Handle input values with type-safe callbacks:

```kotlin
player.openConfirmationDialog {
    common(title = "Profile Settings") {
        body {
            text("Update your profile")
        }
        
        inputs {
            boolean("public", "Public Profile", initial = true) { value ->
                player.sendMessage("Profile visibility: $value")
            }
            
            text("nickname", "Nickname", initial = player.name) { value ->
                player.sendMessage("Nickname: $value")
            }
            
            numberRange("age", "Age", range = 13..100, initial = 18) { value ->
                player.sendMessage("Age: $value")
            }
            
            options("color", "Name Color") {
                add("red", "Red") { player.sendMessage("Selected Red") }
                add("blue", "Blue") { player.sendMessage("Selected Blue") }
                add("green", "Green") { player.sendMessage("Selected Green") }
            }
        }
    }

    yesButton("save", "Save") {}
    noButton("cancel", "Cancel") {}
}
```

### Property Reflection

Automatically bind inputs to properties:

```kotlin
class PlayerSettings {
    var pvpEnabled: Boolean = true
    var nickname: String = "Player"
    var volume: Float = 50f
}

val settings = PlayerSettings()

player.openConfirmationDialog {
    common(title = "Settings") {
        body {
            text("Configure your settings")
        }
        
        inputs {
            boolean("pvp", "PVP Enabled", property = settings::pvpEnabled)
            text("nickname", "Nickname", property = settings::nickname)
            numberRange("volume", "Volume", range = 0f..100f, property = settings::volume)
        }
    }

    yesButton("save", "Save") {}
    noButton("cancel", "Cancel") {}
}
```

### Custom Getters/Setters

Use custom getter/setter functions:

```kotlin
player.openConfirmationDialog {
    common(title = "Custom Storage") {
        body {
            text("Custom value storage")
        }
        
        inputs {
            // Custom getter and setter
          boolean("flag", "Custom Flag", 
                  getter = { player.hasPermission("custom.flag") }, 
                  setter = { value -> 
                    if (value) { 
                      player.addAttachment(plugin, "custom.flag", true) 
                    } 
                  }
          )
          
            // shortcut version
          boolean("fly", "Enable Fly", player::getAllowFlight, player::setAllowFlight)
        }
    }

    yesButton("save", "Save") {}
    noButton("cancel", "Cancel") {}
}
```

### Lifecycle Callbacks

Handle dialog lifecycle events:

```kotlin
player.openConfirmationDialog {
    common(title = "Lifecycle Demo") {
        body {
            text("Dialog lifecycle events")
        }
    }

    onOpen {
        player.sendMessage("Dialog opened!")
    }

    onClose {
        player.sendMessage("Dialog closed!")
    }

    onTick {
        // Called every tick while dialog is open
        player.sendActionBar("Dialog is open...")
    }

    onGlobalClick { buttonId, payload ->
        player.sendMessage("Button clicked: $buttonId")
    }

    yesButton("confirm", "Confirm") {}
    noButton("cancel", "Cancel") {}
}
```

### Sync vs Async Dialogs

Control whether the dialog runs synchronously or asynchronously:

```kotlin
// Async (default)
player.openConfirmationDialog(sync = false) {
    common(title = "Async Dialog") {
        body { text("This dialog runs asynchronously") }
    }
    yesButton("yes", "Yes") {}
    noButton("no", "No") {}
}

// Sync
player.openConfirmationDialog(sync = true) {
    common(title = "Sync Dialog") {
        body { text("This dialog runs synchronously") }
    }
    yesButton("yes", "Yes") {}
    noButton("no", "No") {}
}
```

### Custom Dialog Configuration

Configure dialog behavior with common options:

```kotlin
player.openConfirmationDialog {
    common(
        title = "Custom Dialog",
        externalTitle = "External Title", // Optional external title
        canCloseWithEscape = true,        // Allow closing with ESC
        pause = true,                     // Pause game while dialog is open
        afterAction = DialogAction.CLOSE  // Action after button click
    ) {
        body {
            text("Custom dialog configuration")
        }
        
        inputs {
            boolean("option", "Option")
        }
    }

    yesButton("confirm", "Confirm") {}
    noButton("cancel", "Cancel") {}
}
```

### Button Customization

Customize buttons with tooltips and custom widths:

```kotlin
player.openMultiActionDialog {
    common(title = "Custom Buttons") {
        body {
            text("Buttons with custom styling")
        }
    }

    add(
        key = "action1",
        label = "Custom Action",
        tooltip = "This is a tooltip",
        width = 200,
        callInputs = true
    ) {
        player.sendMessage("Action 1 executed")
    }

    exitButton(
        key = "exit",
        label = "Exit",
        tooltip = "Close the dialog",
        width = 150
    )
}
```

### Dialog Actions

Use the ActionBuilder for complex button actions:

```kotlin
player.openConfirmationDialog {
    common(title = "Action Builder") {
        body {
            text("Complex button actions")
        }
    }

    yesButtonWith("Execute", "Execute complex action") {
        // Add custom actions via ActionBuilder
    }

    noButton("cancel", "Cancel") {}
}
```

## Player Extension Functions

The library provides convenient extension functions for players:

```kotlin
// Open a dialog
player.openDialog(dialog)

// Close the current custom dialog
player.closeCustomDialog()

// Eject the dialog handler
player.ejectDialogHandler()

// Inject a dialog handler
player.injectDialogHandler(dialog)

// Get the current dialog handler
val handler = player.dialogHandlerOrNull()

// Get the current dialog
val dialog = player.dialogOrNull()
```

## Building Dialogs Programmatically

You can also build dialogs without opening them immediately:

```kotlin
val dialog = buildConfirmationDialog {
    common(title = "Pre-built Dialog") {
        body {
            text("This dialog is built but not opened yet")
        }
    }
    yesButton("yes", "Yes") {}
    noButton("no", "No") {}
}

// Open later
dialog.open(player)
```

## Complete Example

Here's a complete example showing various features:

```kotlin
fun showSettingsDialog(player: Player) {
    player.openConfirmationDialog {
        common(title = "Player Settings") {
            body {
                text("Configure your player settings")
                item(ItemStack.of(Material.PLAYER_HEAD), "Your Profile")
            }

            inputs {
                boolean("pvp", "Enable PvP", initial = true) { enabled ->
                    Bukkit.broadcastMessage("${player.name} ${if (enabled) "enabled" else "disabled"} PvP")
                }

                text("nickname", "Nickname", initial = player.name, maxLength = 16) { nickname ->
                    player.displayName = nickname.text()
                    player.sendMessage("Nickname set to: $nickname")
                }

                numberRange("volume", "Volume", range = 0f..100f, initial = 50f, step = 5f) { volume ->
                    player.sendMessage("Volume set to: $volume%")
                }

                options("difficulty", "Difficulty") {
                    add("easy", "Easy")
                    add("normal", "Normal")
                    add("hard", "Hard")
                }
            }
        }

        onOpen {
            player.playSound(player.location, Sound.BLOCK_CHEST_OPEN, 1f, 1f)
        }

        onClose {
            player.playSound(player.location, Sound.BLOCK_CHEST_CLOSE, 1f, 1f)
        }

        yesButton("save", "Save Settings") {
            player.sendMessage("<green>Settings saved successfully!")
        }

        noButton("cancel", "Cancel") {
            player.sendMessage("<red>Settings cancelled")
        }
    }
}
```

```kotlin
fun enumOptionDialog(player: Player) = player.openConfirmationDialog {
  common("My Dialog") {
    inputs {
      options("gamemode", "Gamemode") {
        // automatically set all options from GameMode enum and initial value as player current gamemode
        fromEnum(player.gameMode) {
          player.gameMode = GameMode.valueOf(it)
        }
      }
      
      options("material", "Item in hand Material") {
        // for long enums, is better to use call function
        call { 
          player.itemInHand.type = Material.valueOf(it)
        }
        
        // automatically set all options from Material enum and initial value as player current item in hand
        fromEnum(player.itemInHand.type)
      }
    }
  }

  yesButton("confirm", "Confirm") {
    player.sendMessage("Confirmed")
  }

  noButton("cancel", "Cancel") {
    player.sendMessage("Cancelled")
  }
}
```


## License

This project is licensed under the MIT License - see the LICENSE file for details.

