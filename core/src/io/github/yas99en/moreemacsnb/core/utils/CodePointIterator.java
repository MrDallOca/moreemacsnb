/*
 * Copyright (c) 2015, Yasuhiro Endoh
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the authors nor the names of its contributors may be
 *     used to endorse or promote products derived from this software without
 *     specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package io.github.yas99en.moreemacsnb.core.utils;

import java.util.Iterator;
import java.util.Objects;

public final class CodePointIterator implements Iterator<Integer> {
    private final CharSequence seq;
    private int index;

    public CodePointIterator(CharSequence seq) {
        this(seq, 0);
    }
    
    public CodePointIterator(CharSequence seq, int index) {
        Objects.requireNonNull(seq, "seq is null");
        this.seq = seq;
        setIndex(index);
    }
    
    public void setIndex(int index) {
        if(index < 0 || index > seq.length()) {
            throw new IndexOutOfBoundsException();
        }
        this.index = index;
    }
    
    @Override
    public boolean hasNext() {       
        return index < seq.length();       
    }
    
    public boolean hasPrevious() {       
        return index > 0;
    }
    
    public int index() {
        return index;
    }
    
    @Override
    public Integer next() {      
        int codePoint = Character.codePointAt(seq, index);        
        index += Character.charCount(codePoint);     
        return codePoint;        
    }

    public Integer previous() {      
        int codePoint = Character.codePointBefore(seq, index);        
        index -= Character.charCount(codePoint);     
        return codePoint;        
    }
    
    @Override
    public void remove() {
        throw new UnsupportedOperationException("unsupported");
    }

    public static Iterable<Integer> each(final CharSequence seq) {
        return new Iterable<Integer>() {
            @Override
            public Iterator<Integer> iterator() {
                return new CodePointIterator(seq);
            }
            
        };
    }
}
