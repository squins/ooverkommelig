/*
 * Copyright (C) 2007 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.ooverkommelig.examples.guice.xml

import org.ooverkommelig.ProvidedAdministration

fun main() {
    PhoneFlashMemoryContactsOgd(object : PhoneFlashMemoryContactsOgd.Provided, ProvidedAdministration() {}).Graph().use { graph ->
        println(graph.phone().contacts.findByName("John Doe"))
    }

    PhoneSimCardContactsOgd(object : PhoneSimCardContactsOgd.Provided, ProvidedAdministration() {}).Graph().use { graph ->
        println(graph.phone().contacts.findByName("Jane Doe"))
    }
}
