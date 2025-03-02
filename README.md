# FRB Robot Simulation for Reefscape 2025

This repository contains a Webots simulation of our FRB (First Robotics Battle) robot designed for the 2025 "Reefscape" challenge. The simulation allows for testing and development of robot strategies in a virtual environment before deployment on the physical robot.

## Project Overview

The "Reefscape" challenge requires robots to navigate an underwater-themed arena, collecting coral fragments and placing them in designated reef restoration zones. Our simulation models the robot's movements, sensor interactions, and scoring mechanisms to accurately represent competition conditions.

## Features

- Complete arena simulation with all game elements from the 2025 "Reefscape" challenge
- Realistic physics simulation of robot movement and object interaction
- Sensor feedback matching real-world conditions
- Scoring system to track points during test runs
- Controller implementations for autonomous and teleoperated periods

## Installation Requirements

### Prerequisites

- **Webots** (version 2024b or later)
- **Java Development Kit** (JDK 17 or later)
- **Git**

### Installing Dependencies

#### 1. Install Webots

**Windows:**
```
winget install Cyberbotics.Webots
```

**macOS:**
```
brew install --cask webots
```

**Ubuntu/Debian:**
```
sudo apt update
sudo apt install webots
```

#### 2. Install JDK

**Windows:**
```
winget install Oracle.JDK.17
```

**macOS:**
```
brew install openjdk@17
```

**Ubuntu/Debian:**
```
sudo apt update
sudo apt install openjdk-17-jdk
```

#### 3. Install Git

**Windows:**
```
winget install Git.Git
```

**macOS:**
```
brew install git
```

**Ubuntu/Debian:**
```
sudo apt update
sudo apt install git
```

## Setup Instructions

1. Clone the repository:
   ```
   git clone https://github.com/your-team/frb-reefscape-simulation.git
   cd frb-reefscape-simulation
   ```

2. Open Webots and load the world file:
   ```
   webots worlds/empty.wbt
   ```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
