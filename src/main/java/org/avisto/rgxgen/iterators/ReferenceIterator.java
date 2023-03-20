package org.avisto.rgxgen.iterators;

/* **************************************************************************
   Copyright 2019 Vladislavs Varslavans

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
/* **************************************************************************/

import java.util.NoSuchElementException;

public class ReferenceIterator implements StringIterator {
    private StringIterator aOther;
    private boolean        hasNext = true;
    private String         aLast;

    public void setOther(StringIterator other) {
        aOther = other;
    }

    @Override
    public String next() {
        if (!hasNext()) {
            throw new NoSuchElementException("Cannot return value second time");
        }
        hasNext = false;
        return aOther.current();
    }

    @Override
    public String current() {
        aLast = aOther.current();
        return aOther.current();
    }

    @Override
    public void reset() {
        hasNext = true;
    }

    @Override
    public boolean hasNext() {
        return hasNext || !aOther.current()
                                 .equals(aLast);
    }
}
