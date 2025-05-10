## Java-Minesweeper

# A complete implementation of the classic Minesweeper game using Java Swing
# 🎮 Game Features
Classic Minesweeper gameplay with customizable grid size

Visual indicators:

Bomb icons for mines

Flag markers for suspected mines

Numbered tiles showing adjacent mines

Auto-reveal for empty areas (zero-tile expansion)

Win/lose conditions with dialog notifications

Right-click flagging system

# 🚀 How to Run
javac *.java && java Main

# 🕹️ Game Controls
Action	Result
Left-click	Reveal tile
Right-click	Place/remove flag
Reveal all mines	Game ends (when mine clicked)
Flag all mines	Win condition

# 🧠 Game Logic Highlights

```
private void putMines() {
    Random random = new Random();
    for (int i = 0; i < MINES_NUMBER; i++) {
        // Ensures unique mine positions
    }
}
// Adjacent mine counting
numbers[i][j] += 1; // For each neighboring mine
```
🖼️ Image Resources
The game uses these assets:

mines.jpg - Default tile background

flag.jpg - Flag marker icon

mine.jpeg - Bomb/mine icon

# 📂 Suggested GitHub Description
"Full-featured Minesweeper clone in Java Swing with classic gameplay mechanics, graphical interface, and win/lose conditions. Perfect for learning game development fundamentals!"

® Created by Hussam Alibrahim

