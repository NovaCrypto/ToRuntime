[![Download](https://api.bintray.com/packages/novacrypto/General/ToRuntime/images/download.svg)](https://bintray.com/novacrypto/General/ToRuntime/_latestVersion) [![Build Status](https://travis-ci.org/NovaCrypto/ToRuntime.svg?branch=master)](https://travis-ci.org/NovaCrypto/ToRuntime) [![codecov](https://codecov.io/gh/NovaCrypto/ToRuntime/branch/master/graph/badge.svg)](https://codecov.io/gh/NovaCrypto/ToRuntime)

Improve test coverage by trapping and promoting tricky checked exceptions you don't really expect or can't handle.

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
