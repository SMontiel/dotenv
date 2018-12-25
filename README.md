# dotenv

**dotenv** is a Java library that loads environment variables from a `.env` file. Storing configuration in the environment separate from code is based on [The Twelve-Factor App](http://12factor.net/config) methodology.

Environment variables listed in the host environment override those in `.env` file.  

Use `DotEnv.get("...")` instead of Java's `System.getenv("...")`.  

> Since Java does not provide a way to set environment variables on a currently running process, vars listed in `.env` cannot be set and thus cannot be retrieved using `System.getenv("...")`.

## Usage

Create a `.env` file in the root directory of your project. Add
environment-specific variables on new lines in the form of `NAME=VALUE`.
For example:

```dosini
# formatted as key=value
DB_HOST=localhost
DB_USER=root
DB_PASS=your_super_password
```

That's it.

`DotEnv` now has the keys and values you defined in your `.env` file.

```java
import com.salvadormontiel.dotenv.DotEnv;

DotEnv.get("DB_HOST");
// localhost
```

## Rules

The parsing engine currently supports the following rules:

- `BASIC=basic` becomes `{BASIC: 'basic'}`
- empty lines are skipped
- lines beginning with `#` or `//` are treated as comments