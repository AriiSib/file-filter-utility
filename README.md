[![Java CI with Maven](https://github.com/AriiSib/file-filter-utility/actions/workflows/ci.yml/badge.svg)](https://github.com/AriiSib/file-filter-utility/actions/workflows/ci.yml)

# File Filter Application

**File Filter Application** — is a utility for filtering and processing data from files.
The program accepts files as input, analyzes their content,
and separates the data into types (integers, floating-point numbers, strings).
The filtering result is saved into output files,
and the path to these files can be specified using a special flag. Additionally,
the program supports displaying brief or detailed statistics of the results.

## Application Features

- Reading input files using both absolute and relative paths.
- Specifying the path for saving the filtered results.
- Setting a prefix for the names of output files.
- Filtering data into integers, floating-point numbers, and strings.
- Supporting multiple file processing in a single run.
- Generating full or brief statistics based on data types.
- Handling input/output errors.
- Notifying users of invalid input.

## Stack

- **Java 21**
- **Maven 3.9.6**
- **Apache Commons CLI (1.9.0)**
- **Apache Maven Shade (3.6.0)**
- **JUnit 5 (5.11.0)**
- **Mockito (5.13.0)**
- **[GitHub Actions](https://github.com/AriiSib/file-filter-utility/actions)**

# Installation

1. Clone the project repository:
   ```bash
   git clone https://github.com/AriiSib/file-filter-utility.git
   ```

2. Navigate to the project directory:
   ```bash
   cd file-filter-utility
   ```

3. Build the project using Maven:
   ```bash
    mvn clean install
   ```

4. Navigate to the target directory:
   ```bash
   cd target
   ```

# Usage

## Running the Program

You can run the application by passing the necessary parameters via the command line:

   ```bash
    java -jar util.jar [options] <input_file_1> <input_file_2> ...
   ```

## Options

- ```-f```: Enables detailed statistics output by data types (integers, floats, strings)
- ```-s```: Enables brief statistics output showing the number of recorded elements
- ```-p <file_prefix>```: Sets a prefix for output file names
- ````-o <output_file>````: Specifies the path for the output file where filtered data will be saved

## Usage Examples

1. Filtering data from a file located in the current directory and saving the results to output files in the same
   directory:
    ```bash
   java -jar util.jar in1.txt
    ```

2. Filtering multiple files from the current directory and saving the results in the same directory, with brief
   statistics output:
    ```bash
   java -jar -s util.jar in1.txt in2.txt
    ```

3. Filtering multiple files: in1.txt from the current directory and in2.txt from a specified directory,
   displaying full statistics, appending results to existing files, assigning the prefix "sample-" to each output file,
   and saving the results to a specified path:
    ```bash
   java -jar util.jar -f -a -p sample- -o <.../some/path> <.../some/path/in2.txt> in1.txt
    ```

## Testing

The project uses JUnit 5 and Mockito for testing. To run the tests, execute the following command:

```bash
   mvn test
```

The tests include:

- File path handling tests (FilePathService)
- File reading service tests (FileReaderService)
- File writing service tests (FileWriterService)
- File processing service tests (FileProcessService)
- Data filtering tests (DataFilterService)
- Statistics generation tests (StatisticsService)
