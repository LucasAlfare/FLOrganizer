/**
 * Enumeration to hold all the events that can transit over the application flow.
 *
 * These events are not a data value, just works to "flag" someone interested that
 * it occurred. Or in other words, they can work as "boolean" directives, saying
 * if the event occurred or no.
 *
 * In some contexts, the event can be sent to listeners with a data object together
 * then, once sometimes is useful to handle the different calls of the same event,
 * for example.
 *
 * Also, the most events are documented with the format of data that it can hold
 * when fired from somewhere, useful to keep track of what is needed to implement
 * something.
 *
 * Normally, these data objects are written in a nullable [Any] variable, which
 * should be cast to the appropriated types according to event definition from here.
 */
enum class AppEvent {
  PatientIdUpdate,
  ExamsUpdate,

  CreatePatient,
  RemovePatient,
  PatientsUpdate
}

/**
 * This class is used to make children to be able to receive events from other [EventManageable]
 * instances. Also, this class is used to make any children be able to propagate events to other
 * interested [EventManageable] instances.
 */
abstract class EventManageable {

  /**
   * Holds all current objects that are listening to this instance.
   */
  var listeners = mutableListOf<EventManageable>()

  /**
   * Function that offers a custom initialization block.
   *
   * Normally this is the first function to be called, after
   * constructor and/or native init blocks.
   */
  abstract fun init()

  /**
   * Used to handle any kind of incoming event/data from outside
   * the instance.
   */
  abstract fun onEvent(event: AppEvent, data: Any?, origin: Any?)

  /**
   * Takes any object that can listen/handle events and adds it in this instance.
   * It will only add if is not added yet.
   */
  fun addListener(l: EventManageable) {
    if (!listeners.contains(l)) listeners.add(l)
  }

  /**
   * Function that pass its params to all previously added listeners of this instance.
   */
  fun notifyListeners(event: AppEvent, data: Any? = null, origin: Any? = null) {
    listeners.forEach {
      it.onEvent(event, data, origin)
    }
  }
}

/**
 * Custom implementation of a class that can be listened and emit
 * events to others.
 *
 * The appropriated use of this class is to work together UI components
 * in order to make then emit application events and receive then as
 * well.
 */
class UIManager : EventManageable() {

  private val callbacks = mutableListOf<(AppEvent, Any?) -> Unit>()

  override fun init() {}

  override fun onEvent(event: AppEvent, data: Any?, origin: Any?) {
    callbacks.forEach { callback ->
      callback(event, data)
    }
  }

  fun addCallback(callback: (AppEvent, Any?) -> Unit): (AppEvent, Any?) -> Unit {
    if (!callbacks.contains(callback)) callbacks.add(callback)
    return callback
  }

  fun removeCallback(callback: (AppEvent, Any?) -> Unit) {
    callbacks.remove(callback)
  }
}

fun setupManagers(vararg managers: EventManageable) {
  managers.forEach { m1 ->
    managers.forEach { m2 ->
      if (m2 != m1) {
        m1.addListener(m2)
      }
    }
  }

  managers.forEach { it.init() }
}