# OOverkommelig

Documentation can be found at http://ooverkommelig.org

Or take a look at [the examples](https://github.com/squins/ooverkommelig/tree/master/examples/src/main/kotlin/org/ooverkommelig/examples).

## Download

To add OOverkommelig to your Gradle project, add the following to your build script written in Groovy:

    repositories {
        maven {
            url 'https://dl.bintray.com/squins/Squins'
        }
    }
    
    dependencies {
        implementation 'org.ooverkommelig:ooverkommelig:1alpha11'
    }

And the following to your build script written in Kotlin:

    repositories {
        maven {
            url = uri("https://dl.bintray.com/squins/Squins")
        }
    }
    
    dependencies {
        // For JavaScript (which does not support all features):
        implementation("org.ooverkommelig:ooverkommelig-js:1beta1")
        // For the JVM:
        implementation("org.ooverkommelig:ooverkommelig-jvm8:1beta1")
    }

## License

    MIT License
    
    Copyright (c) 2016 Squins IT Solutions
    
    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:
    
    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.
    
    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
