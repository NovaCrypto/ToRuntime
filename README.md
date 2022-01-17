![Maven Central](https://img.shields.io/maven-central/v/io.github.novacrypto/ToRuntime)

Improve test coverage by trapping and promoting tricky checked exceptions you don't really expect or can't handle.

# Install

Using:

```
repositories {
    mavenCentral()
}
```

Add dependency:

```
dependencies {
    implementation 'io.github.novacrypto:ToRuntime:2022.01.17@jar'
}

```

# Usage

Migrate from:

```
Result result;
try {
    result = somethingThatThrowsChecked();
} catch (TheChecked checked) {
    throw new RuntimeException(checked); // tricky to cover this line
}
```

To:

```
import static io.github.novacrypto.toruntime.CheckedExceptionToRuntime.toRuntime;
```

```
Result result = toRuntime(() -> somethingThatThrowsChecked());
```
