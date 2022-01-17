/*
 *  ToRuntime library, Java promotions of checked exceptions to runtime exceptions
 *  Copyright (C) 2017-2022 Alan Evans, NovaCrypto
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 *  Original source: https://github.com/NovaCrypto/ToRuntime
 *  You can contact the authors via github issues.
 */

package io.github.novacrypto.toruntime;

import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import static io.github.novacrypto.toruntime.CheckedExceptionToRuntime.toRuntime;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public final class CheckedExceptionToRuntimeTests {

    @Test
    public void forCodeCoverageOnly_create() {
        //noinspection ObviousNullCheck
        assertNotNull(new CheckedExceptionToRuntime());
    }

    @Test(expected = RuntimeException.class)
    public void noException() {
        assertEquals("Hello",
                toRuntime(() -> "Hello"));
    }

    @Test
    public void noException2() {
        assertEquals("String",
                toRuntime(() -> "String"));
    }

    @Test
    public void invokesOnceOnly() {
        IntSpy intSpy = new IntSpy();
        toRuntime(intSpy::increment);
        assertEquals(1, intSpy.invocations);
    }

    @Test
    public void checkedException1() {
        assertThatThrownBy(() ->
                toRuntime(() -> {
                            throw new NoSuchAlgorithmException();
                        }
                ))
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(NoSuchAlgorithmException.class);
    }

    @Test
    public void checkedException2() {
        assertThatThrownBy(() ->
                toRuntime(() -> {
                            throw new UnsupportedEncodingException();
                        }
                ))
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(UnsupportedEncodingException.class);
    }

    @Test
    public void noExceptionVoidInvokesOnce() {
        VoidSpy sb = new VoidSpy();
        toRuntime(sb::voidMethod);
        assertEquals(1, sb.invocations);
    }

    @Test
    public void checkedExceptionVoidResult() {
        assertThatThrownBy(() ->
                toRuntime(this::failWithIndexOutOfBoundsException))
                .isInstanceOf(RuntimeException.class)
                .hasCauseInstanceOf(IndexOutOfBoundsException.class);
    }

    private void failWithIndexOutOfBoundsException() {
        throw new IndexOutOfBoundsException();
    }

    private static class IntSpy {
        int invocations;

        int increment() {
            invocations++;
            return invocations;
        }
    }

    private static class VoidSpy {

        int invocations;

        void voidMethod() {
            invocations++;
        }

    }
}