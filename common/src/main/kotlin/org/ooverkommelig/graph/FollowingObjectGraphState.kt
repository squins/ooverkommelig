package org.ooverkommelig.graph

internal interface FollowingObjectGraphState : ObjectGraphState {
    fun enter(graph: ObjectGraphImpl)
}
