/**
 * ============================================================
 * File : RMDMD5.java
 * Description :
 *
 * Package : com.ge.trans.rmd.utilities
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : Jun 9, 2010
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2010 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.security.utils;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.utilities.RMDCommonUtility;

/**
 * @Author :
 * @Version : 1.0
 * @Date Created : Jun 9, 2010
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description : This class computes MD5 hashes.To compute the message digest
 *              of a chunk of bytes, create an MD5 object 'md5', call
 *              md5.update() as needed on buffers full of bytes, and then call
 *              md5.md5final(), which will fill a supplied 16-byte array with
 *              the digest. This code implements the MD5 message-digest
 *              algorithm.
 * @History :
 */
public final class RMDMD5 {

    /**
     * 
     */
    private Fcore f1 = new Fcore() {

        /*
         * (non-Javadoc)
         * @see com.ge.trans.rmd.utilities.RMDMD5.Fcore#f(int, int, int)
         */
        @Override
        int f(final int x, final int y, final int z) {
            return (z ^ (x & (y ^ z)));
        }
    };
    /**
     * 
     */
    private Fcore f2 = new Fcore() {

        /*
         * (non-Javadoc)
         * @see com.ge.trans.rmd.utilities.RMDMD5.Fcore#f(int, int, int)
         */
        @Override
        int f(final int x, final int y, final int z) {
            return (y ^ (z & (x ^ y)));
        }
    };
    private Fcore f3 = new Fcore() {

        @Override
        int f(final int x, final int y, final int z) {
            return (x ^ y ^ z);
        }
    };
    /**
     * 
     */
    private Fcore f4 = new Fcore() {

        /*
         * (non-Javadoc)
         * @see com.ge.trans.rmd.utilities.RMDMD5.Fcore#f(int, int, int)
         */
        @Override
        int f(final int x, final int y, final int z) {
            return (y ^ (x | ~z));
        }
    };
    // This Java code makes an effort to avoid sign traps.
    // buf[] is where the hash accumulates.
    private long bits; // This is the count of bits hashed so far.
    private int buf[]; // These were originally unsigned ints.
    private byte in[]; // This is a buffer where we stash bytes until we have
    // enough (64) to perform a transform operation.
    private int inint[];

    /**
     * 
     */
    private RMDMD5() {
        buf = new int[4];
        // fill the hash accumulator with a seed value
        buf[0] = 0x67452301;
        buf[1] = 0xefcdab89;
        buf[2] = 0x98badcfe;
        buf[3] = 0x10325476;
        // initially, we've hashed zero bits
        bits = 0L;
        in = new byte[64];
        inint = new int[16];
    }

    /**
     * @Author:
     * @param newbuf
     * @Description:
     */
    public void update(final byte[] newbuf) {
        update(newbuf, 0, newbuf.length);
    }

    /**
     * @Author:
     * @param newbuf
     * @param length
     * @Description:
     */
    public void update(final byte[] newbuf, final int length) {
        update(newbuf, 0, length);
    }

    /**
     * @Author:
     * @param newbuf
     * @param bufstart
     * @param buflen
     * @Description:
     */
    public void update(final byte[] newbuf, final int bufstart, final int buflen) {
        int temp;
        int len = buflen;
        int local = bufstart;
        // shash old bits value for the "Bytes already in" computation
        // just below.
        temp = (int) bits; // (int) cast should just drop high bits, I hope
        /*
         * update bitcount Java has a 64-bit long, which is just what the code
         * really wants.
         */
        bits += len << 3;
        temp = (temp >>> 3) & 0x3f; /* Bytes already in this->in */
        /* Handle any leading odd-sized chunks */
        /* (that is, any left-over chunk left by last update() */
        if (temp != 0) {
            final int position = temp;
            temp = 64 - temp;
            if (len < temp) {
                System.arraycopy(newbuf, local, in, position, len);
                return;
            }
            System.arraycopy(newbuf, local, in, position, temp);
            transform();
            local += temp;
            len -= temp;
        }
        /* Process data in 64-byte chunks */
        while (len >= 64) {
            System.arraycopy(newbuf, local, in, 0, 64);
            transform();
            local += 64;
            len -= 64;
        }
        /* Handle any remaining bytes of data. */
        /* that is, stash them for the next update(). */
        System.arraycopy(newbuf, local, in, 0, len);
    }

    /**
     * @Author:
     * @param digest
     * @Description: Final wrapup - pad to 64-byte boundary with the bit pattern
     *               1 0* (64-bit count of bits processed, MSB-first)
     */
    public void md5final(final byte[] digest) {
        /* "finals" is a poor method name in Java. :v) */
        int count;
        int paddingPointer; // in original code, this is a pointer; in this java
                            // code
        // it's an index into the array this->in.
        /* Compute number of bytes mod 64 */
        count = (int) ((bits >>> 3) & 0x3F);
        /*
         * Set the first char of padding to 0x80. This is safe since there is
         * always at least one byte free
         */
        paddingPointer = count;
        in[paddingPointer++] = (byte) 0x80;
        /* Bytes of padding needed to make 64 bytes */
        count = 64 - 1 - count;
        /* Pad out to 56 mod 64 */
        if (count < 8) {
            /* Two lots of padding: Pad the first block to 64 bytes */
            zeroByteArray(in, paddingPointer, count);
            transform();
            /* Now fill the next block with 56 bytes */
            zeroByteArray(in, 0, 56);
        } else {
            /* Pad block to 56 bytes */
            zeroByteArray(in, paddingPointer, count - 8);
        }
        /* Append length in bits and transform */
        // Could use a PUT_64BIT... func here. This is a fairly
        // direct translation from the C code, where bits was an array
        // of two 32-bit ints.
        final int lowbits = (int) bits;
        final int highbits = (int) (bits >>> 32);
        put32BitLsbFirst(in, 56, lowbits);
        put32BitLsbFirst(in, 60, highbits);
        transform();
        put32BitLsbFirst(digest, 0, buf[0]);
        put32BitLsbFirst(digest, 4, buf[1]);
        put32BitLsbFirst(digest, 8, buf[2]);
        put32BitLsbFirst(digest, 12, buf[3]);
        /* zero sensitive data */
        /*
         * notice this misses any sneaking out on the stack.
         */
        zeroByteArray(in);
        zeroIntArray(buf);
        bits = 0;
        zeroIntArray(inint);
    }

    /**
     * @Author:
     * @param args
     * @Description: This main method is to test the encryption easily
     */
    /*
     * public static void main(String[] args) { String input = "eoadev"; }
     */

    /**
     * @Author:
     * @param text
     * @return
     * @Description:
     */
    public static String encryptMD5(final String text) {
        if ((null == text) || RMDCommonConstants.EMPTY_STRING.equals(text)) {
            return null;
        }
        String passwrd = text;
        final int len = text.length() + (text.length() / 2);
        for (int index = 0; index < len; index++) {
            passwrd = encrypt(passwrd + RMDCommonUtility.getIntValue(String.valueOf((len % 3 - (len * 796)))));
        }
        return passwrd;
    }

    /**
     * @Author:
     * @param text
     * @return
     * @Description:
     */
    public static String encrypt(final String text) {
        if ((null == text) || RMDCommonConstants.EMPTY_STRING.equals(text)) {
            return null;
        }
        // arbitrary buffer length designed to irritate update()
        final RMDMD5 md = new RMDMD5();
        final byte out[] = new byte[16];
        md.update(text.getBytes());
        md.md5final(out);
        return dumpBytes(out).toString();
    }

    /**
     * @Author:
     * @param byteArray
     * @Description:
     */
    private void zeroByteArray(final byte[] byteArray) {
        zeroByteArray(byteArray, 0, byteArray.length);
    }

    /**
     * @Author:
     * @param byteArray
     * @param start
     * @param length
     * @Description:
     */
    private void zeroByteArray(final byte[] byteArray, final int start, final int length) {
        setByteArray(byteArray, (byte) 0, start, length);
    }

    /**
     * @Author:
     * @param byteArray
     * @param val
     * @param start
     * @param length
     * @Description:
     */
    private void setByteArray(final byte[] byteArray, final byte val, final int start, final int length) {
        int i;
        final int end = start + length;
        for (i = start; i < end; i++) {
            byteArray[i] = val;
        }
    }

    /**
     * @Author:
     * @param intArray
     * @Description:
     */
    private void zeroIntArray(final int[] intArray) {
        zeroIntArray(intArray, 0, intArray.length);
    }

    /**
     * @Author:
     * @param intArray
     * @param start
     * @param length
     * @Description:
     */
    private void zeroIntArray(final int[] intArray, final int start, final int length) {
        setIntArray(intArray, 0, start, length);
    }

    /**
     * @Author:
     * @param a
     * @param val
     * @param start
     * @param length
     * @Description:
     */
    private void setIntArray(final int[] a, final int val, final int start, final int length) {
        int i;
        final int end = start + length;
        for (i = start; i < end; i++) {
            a[i] = val;
        }
    }

    /**
     * @Author:
     * @param f
     * @param w
     * @param x
     * @param y
     * @param z
     * @param data
     * @param s
     * @return
     * @Description:
     */
    private int stepMD5(final Fcore f, final int w, final int x, final int y, final int z, final int data,
            final int s) {
        int localNew = w;
        localNew += f.f(x, y, z) + data;
        localNew = localNew << s | localNew >>> (32 - s);
        localNew += x;
        return localNew;
    }

    /**
     * @Author:
     * @Description:
     */
    private void transform() {
        /* load in[] byte array into an internal int array */
        int index;
        int[] inInt = new int[16];
        for (index = 0; index < 16; index++) {
            inInt[index] = get32BitLSBFirst(in, 4 * index);
        }
        int intA, intB, intC, intD;
        intA = buf[0];
        intB = buf[1];
        intC = buf[2];
        intD = buf[3];
        intA = stepMD5(f1, intA, intB, intC, intD, inInt[0] + 0xd76aa478, 7);
        intD = stepMD5(f1, intD, intA, intB, intC, inInt[1] + 0xe8c7b756, 12);
        intC = stepMD5(f1, intC, intD, intA, intB, inInt[2] + 0x242070db, 17);
        intB = stepMD5(f1, intB, intC, intD, intA, inInt[3] + 0xc1bdceee, 22);
        intA = stepMD5(f1, intA, intB, intC, intD, inInt[4] + 0xf57c0faf, 7);
        intD = stepMD5(f1, intD, intA, intB, intC, inInt[5] + 0x4787c62a, 12);
        intC = stepMD5(f1, intC, intD, intA, intB, inInt[6] + 0xa8304613, 17);
        intB = stepMD5(f1, intB, intC, intD, intA, inInt[7] + 0xfd469501, 22);
        intA = stepMD5(f1, intA, intB, intC, intD, inInt[8] + 0x698098d8, 7);
        intD = stepMD5(f1, intD, intA, intB, intC, inInt[9] + 0x8b44f7af, 12);
        intC = stepMD5(f1, intC, intD, intA, intB, inInt[10] + 0xffff5bb1, 17);
        intB = stepMD5(f1, intB, intC, intD, intA, inInt[11] + 0x895cd7be, 22);
        intA = stepMD5(f1, intA, intB, intC, intD, inInt[12] + 0x6b901122, 7);
        intD = stepMD5(f1, intD, intA, intB, intC, inInt[13] + 0xfd987193, 12);
        intC = stepMD5(f1, intC, intD, intA, intB, inInt[14] + 0xa679438e, 17);
        intB = stepMD5(f1, intB, intC, intD, intA, inInt[15] + 0x49b40821, 22);
        intA = stepMD5(f2, intA, intB, intC, intD, inInt[1] + 0xf61e2562, 5);
        intD = stepMD5(f2, intD, intA, intB, intC, inInt[6] + 0xc040b340, 9);
        intC = stepMD5(f2, intC, intD, intA, intB, inInt[11] + 0x265e5a51, 14);
        intB = stepMD5(f2, intB, intC, intD, intA, inInt[0] + 0xe9b6c7aa, 20);
        intA = stepMD5(f2, intA, intB, intC, intD, inInt[5] + 0xd62f105d, 5);
        intD = stepMD5(f2, intD, intA, intB, intC, inInt[10] + 0x02441453, 9);
        intC = stepMD5(f2, intC, intD, intA, intB, inInt[15] + 0xd8a1e681, 14);
        intB = stepMD5(f2, intB, intC, intD, intA, inInt[4] + 0xe7d3fbc8, 20);
        intA = stepMD5(f2, intA, intB, intC, intD, inInt[9] + 0x21e1cde6, 5);
        intD = stepMD5(f2, intD, intA, intB, intC, inInt[14] + 0xc33707d6, 9);
        intC = stepMD5(f2, intC, intD, intA, intB, inInt[3] + 0xf4d50d87, 14);
        intB = stepMD5(f2, intB, intC, intD, intA, inInt[8] + 0x455a14ed, 20);
        intA = stepMD5(f2, intA, intB, intC, intD, inInt[13] + 0xa9e3e905, 5);
        intD = stepMD5(f2, intD, intA, intB, intC, inInt[2] + 0xfcefa3f8, 9);
        intC = stepMD5(f2, intC, intD, intA, intB, inInt[7] + 0x676f02d9, 14);
        intB = stepMD5(f2, intB, intC, intD, intA, inInt[12] + 0x8d2a4c8a, 20);
        intA = stepMD5(f3, intA, intB, intC, intD, inInt[5] + 0xfffa3942, 4);
        intD = stepMD5(f3, intD, intA, intB, intC, inInt[8] + 0x8771f681, 11);
        intC = stepMD5(f3, intC, intD, intA, intB, inInt[11] + 0x6d9d6122, 16);
        intB = stepMD5(f3, intB, intC, intD, intA, inInt[14] + 0xfde5380c, 23);
        intA = stepMD5(f3, intA, intB, intC, intD, inInt[1] + 0xa4beea44, 4);
        intD = stepMD5(f3, intD, intA, intB, intC, inInt[4] + 0x4bdecfa9, 11);
        intC = stepMD5(f3, intC, intD, intA, intB, inInt[7] + 0xf6bb4b60, 16);
        intB = stepMD5(f3, intB, intC, intD, intA, inInt[10] + 0xbebfbc70, 23);
        intA = stepMD5(f3, intA, intB, intC, intD, inInt[13] + 0x289b7ec6, 4);
        intD = stepMD5(f3, intD, intA, intB, intC, inInt[0] + 0xeaa127fa, 11);
        intC = stepMD5(f3, intC, intD, intA, intB, inInt[3] + 0xd4ef3085, 16);
        intB = stepMD5(f3, intB, intC, intD, intA, inInt[6] + 0x04881d05, 23);
        intA = stepMD5(f3, intA, intB, intC, intD, inInt[9] + 0xd9d4d039, 4);
        intD = stepMD5(f3, intD, intA, intB, intC, inInt[12] + 0xe6db99e5, 11);
        intC = stepMD5(f3, intC, intD, intA, intB, inInt[15] + 0x1fa27cf8, 16);
        intB = stepMD5(f3, intB, intC, intD, intA, inInt[2] + 0xc4ac5665, 23);
        intA = stepMD5(f4, intA, intB, intC, intD, inInt[0] + 0xf4292244, 6);
        intD = stepMD5(f4, intD, intA, intB, intC, inInt[7] + 0x432aff97, 10);
        intC = stepMD5(f4, intC, intD, intA, intB, inInt[14] + 0xab9423a7, 15);
        intB = stepMD5(f4, intB, intC, intD, intA, inInt[5] + 0xfc93a039, 21);
        intA = stepMD5(f4, intA, intB, intC, intD, inInt[12] + 0x655b59c3, 6);
        intD = stepMD5(f4, intD, intA, intB, intC, inInt[3] + 0x8f0ccc92, 10);
        intC = stepMD5(f4, intC, intD, intA, intB, inInt[10] + 0xffeff47d, 15);
        intB = stepMD5(f4, intB, intC, intD, intA, inInt[1] + 0x85845dd1, 21);
        intA = stepMD5(f4, intA, intB, intC, intD, inInt[8] + 0x6fa87e4f, 6);
        intD = stepMD5(f4, intD, intA, intB, intC, inInt[15] + 0xfe2ce6e0, 10);
        intC = stepMD5(f4, intC, intD, intA, intB, inInt[6] + 0xa3014314, 15);
        intB = stepMD5(f4, intB, intC, intD, intA, inInt[13] + 0x4e0811a1, 21);
        intA = stepMD5(f4, intA, intB, intC, intD, inInt[4] + 0xf7537e82, 6);
        intD = stepMD5(f4, intD, intA, intB, intC, inInt[11] + 0xbd3af235, 10);
        intC = stepMD5(f4, intC, intD, intA, intB, inInt[2] + 0x2ad7d2bb, 15);
        intB = stepMD5(f4, intB, intC, intD, intA, inInt[9] + 0xeb86d391, 21);
        buf[0] += intA;
        buf[1] += intB;
        buf[2] += intC;
        buf[3] += intD;
    }

    /**
     * @Author:
     * @param byteArray
     * @param offSet
     * @return
     * @Description:
     */
    private int get32BitLSBFirst(final byte[] byteArray, final int offSet) {
        return (byteArray[offSet + 0] & 0xff) | ((byteArray[offSet + 1] & 0xff) << 8)
                | ((byteArray[offSet + 2] & 0xff) << 16) | ((byteArray[offSet + 3] & 0xff) << 24);
    }

    /**
     * @Author:
     * @param byteArray
     * @param offSet
     * @param value
     * @Description:
     */
    private void put32BitLsbFirst(final byte[] byteArray, final int offSet, final int value) {
        byteArray[offSet + 0] = (byte) (value & 0xff);
        byteArray[offSet + 1] = (byte) ((value >> 8) & 0xff);
        byteArray[offSet + 2] = (byte) ((value >> 16) & 0xff);
        byteArray[offSet + 3] = (byte) ((value >> 24) & 0xff);
    }

    /**
     * @Author:
     * @param bytes
     * @return
     * @Description:
     */
    private static String dumpBytes(final byte[] bytes) {
        int index;
        final StringBuilder sb = new StringBuilder();
        for (index = 0; index < bytes.length; index++) {
            if ((index % 32 == 0) && (index != 0)) {
                sb.append("\n");
            }
            String hexString = Integer.toHexString(bytes[index]);
            if (hexString.length() < 2) {
                hexString = "0" + hexString;
            }
            if (hexString.length() > 2) {
                hexString = hexString.substring(hexString.length() - 2);
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    /***************************************************************************
     * @Author :
     * @Version : 1.0
     * @Date Created: Jun 9, 2010
     * @Date Modified :
     * @Modified By :
     * @Contact :
     * @Description : In this Java version, we pass an Fcore object to represent
     *              the inner macro, and the MD5STEP() method performs the work
     *              of the outer macro. It would be good if this could all get
     *              inlined, but it would take a pretty aggressive compiler to
     *              inline away the dynamic method lookup made by MD5STEP to get
     *              to the Fcore.f function.
     * @History :
     **************************************************************************/
    private abstract class Fcore {

        /**
         * @Author:
         * @param x
         * @param y
         * @param z
         * @return
         * @Description:
         */
        abstract int f(int x, int y, int z);
    }
}
//
