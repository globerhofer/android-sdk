/*
 *  Copyright 2011 Eric F. Savage, code@efsavage.com
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package com.ajah.geo.us;

/**
 * Thrown when there is an attempt to set a ZIP code to an illegal value.
 *
 * @author Eric F. Savage <code@efsavage.com>
 * @see ZipCode
 */
public class IllegalZipCodeFormatException extends IllegalArgumentException {

    private static final long serialVersionUID = 6367113304959164109L;

    /**
     * @see IllegalArgumentException#IllegalArgumentException(String)
     */
    public IllegalZipCodeFormatException(final String zip) {
        super(zip + " is not a valid ZIP code");
    }

}
