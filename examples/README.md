# JDatePicker Examples

This folder contains example applications demonstrating the usage of JDatePicker components.

## SimpleTestApp

A simple Swing application that demonstrates both `JDatePanel` and `JDatePicker` components.

### Features

- **JDatePanel**: An embedded calendar component that can be directly added to any panel
- **JDatePicker**: A popup calendar with a text field (similar to a date picker in web forms)
- Interactive buttons to:
  - Read and display selected dates
  - Set both components to today's date
  - Clear date selections

### Building

First, make sure the library is built:

```bash
cd ../library
mvn clean package
```

### Compiling the Example

```bash
cd examples
javac -cp ../library/target/jdatepicker-2.0.0-SNAPSHOT.jar SimpleTestApp.java
```

### Running the Example

```bash
java -cp ".:../library/target/jdatepicker-2.0.0-SNAPSHOT.jar" SimpleTestApp
```

On Windows, use semicolon instead of colon:

```cmd
java -cp ".;..\library\target\jdatepicker-2.0.0-SNAPSHOT.jar" SimpleTestApp
```

### Usage

1. Click on dates in the **JDatePanel** to select them directly
2. Click the button next to **JDatePicker** to open the popup calendar
3. Use **Read Selected Dates** to see what dates are currently selected
4. Use **Set Today's Date** to set both components to the current date
5. Use **Clear Dates** to deselect dates from both components

### Code Overview

The example demonstrates:

- Creating `JDatePanel` and `JDatePicker` with default constructors
- Reading selected dates using `getModel().getValue()`
- Setting dates programmatically using `getModel().setDate()`
- Managing selection state with `getModel().setSelected()`
- Working with the default `UtilCalendarModel` which uses `java.util.Calendar`

### Key API Usage

```java
// Create components
JDatePanel datePanel = new JDatePanel();
JDatePicker datePicker = new JDatePicker();

// Read selected date
Calendar date = (Calendar) datePanel.getModel().getValue();

// Set a date
datePanel.getModel().setDate(2024, 0, 15); // January 15, 2024 (month is 0-indexed)
datePanel.getModel().setSelected(true);

// Clear selection
datePanel.getModel().setSelected(false);
```
