# 🚢 Nautilus - Maritime Fleet Management System

**Nautilus** is a console-based, Object-Oriented Java application designed to simulate and manage the complex operations of a maritime port. It handles the registration, allocation, and tracking of different types of vessels, engines, and crew members.

This project was developed as part of the Object-Oriented Programming (OOP) coursework for the Computer Engineering degree at ENIDH.

## 🎯 Key Features & Technical Highlights

This system was architected with strict adherence to **OOP principles** and **Clean Code** practices:

* **Inheritance & Polymorphism:** Implementation of a base `Embarcacao` (Vessel) class, which is extended by specific types such as `NavioSuporte`, `BarcoPatrulha`, and `LanchaRapida`. Each child class overrides specific behaviors and manages its unique components (e.g., radars, spotlights).
* **Encapsulation & Validation:** Strict access modifiers (`private`) are used across all classes. Business rules are enforced directly in the constructors and setters. For example, the `Marinheiro` (Sailor) class automatically validates rank and age requirements (e.g., an Officer must be at least 35 years old).
* **Data Persistence:** The system state can be fully saved and recovered using **Java File I/O** (Serialization), ensuring no data is lost between sessions.
* **Interactive CLI:** A robust Command Line Interface allowing users to manage maintenance (CRUD operations for vessels and sailors) and active port operations (missions, radar detection, sailor allocation).

## 🛠️ Tech Stack
* **Language:** Java 21
* **Build Tool:** Maven
* **Paradigm:** Object-Oriented Programming (OOP)

## 🚀 How to Run

1. Clone the repository:
   ```bash
   git clone [https://github.com/mcorreiaroque/Nautilus.git](https://github.com/mcorreiaroque/Nautilus.git)