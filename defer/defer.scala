package tremolo.util.control.defer

object DeferScope {
    import collection.mutable.ListBuffer

    def apply[R](f: DeferMemory => R): R = {
        val deferMemory = new DeferMemory
        val retVal = f(deferMemory)
        deferMemory.run()
        return retVal
    }

    private class DeferMemory {
        private val memory = ListBuffer[() => Any]()

        def apply(f: => Any) = memory += {() => f}
        private[DeferScope] def run() = memory.foreach(f => f())
    }
}
