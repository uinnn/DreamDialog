# DreamDialog
Dialog API for easily create powerful dialogs

## Dependency
DreamDialog is available on Maven Central.
```gradle
repositories {
  mavenCentral()
}

dependencies {
  implementation("io.github.vernearth-dream-dialog-1.0.0")
}
```

## Simple usage
```kotlin
/**
 * I always recommend using this way of creating dialogs
 */
fun myDialog(player: Player) = player.openConfirmationDialog {
  common(title = "My Dialog", canCloseWithEscape = false) {
    body {
      text("example")
      item(ItemStack.of(Material.BARRIER))
    }

    inputs {
      boolean("boolean", "My Boolean Input")
      text("text", "My Text Input")
      numberRange("number", "My Number Input", 1f..10f)
      options("option", "My Option Input") {
        add("first", "First Option")
        add("second", "Second Option")
        add("third", "Third Option")
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
