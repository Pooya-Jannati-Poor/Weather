package ir.arinateam.weather.utils

import kotlin.jvm.JvmOverloads
import java.lang.IllegalArgumentException
import java.util.*

class JalaliCal : Calendar {
    private var cal: GregorianCalendar? = null

    constructor(aLocale: Locale?) : this(TimeZone.getDefault(), aLocale) {}

    @JvmOverloads
    constructor(
        zone: TimeZone = TimeZone.getDefault(),
        aLocale: Locale? = Locale.getDefault()
    ) : super(zone, aLocale) {
        Companion.timeZone = zone
        val calendar = getInstance(zone, aLocale)
        var yearMonthDate = YearMonthDate(calendar[YEAR], calendar[MONTH], calendar[DATE])
        yearMonthDate = gregorianToJalali(yearMonthDate)
        set(yearMonthDate.year, yearMonthDate.month, yearMonthDate.day)
        complete()
    }

    constructor(year: Int, month: Int, dayOfMonth: Int) : this(
        year,
        month,
        dayOfMonth,
        0,
        0,
        0,
        0
    ) {
    }

    constructor(
        year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int,
        minute: Int
    ) : this(year, month, dayOfMonth, hourOfDay, minute, 0, 0) {
    }

    constructor(
        year: Int, month: Int, dayOfMonth: Int, hourOfDay: Int,
        minute: Int, second: Int
    ) : this(year, month, dayOfMonth, hourOfDay, minute, second, 0) {
    }

    internal constructor(
        year: Int, month: Int, dayOfMonth: Int,
        hourOfDay: Int, minute: Int, second: Int, millis: Int
    ) : super() {
        this[YEAR] = year
        this[MONTH] = month
        this[DAY_OF_MONTH] = dayOfMonth
        if (hourOfDay >= 12 && hourOfDay <= 23) {
            this[AM_PM] = PM
            this[HOUR] = hourOfDay - 12
        } else {
            this[HOUR] = hourOfDay
            this[AM_PM] = AM
        }
        this[HOUR_OF_DAY] = hourOfDay
        this[MINUTE] = minute
        this[SECOND] = second
        this[MILLISECOND] = millis
        val yearMonthDate = jalaliToGregorian(YearMonthDate(fields[1], fields[2], fields[5]))
        cal = GregorianCalendar(
            yearMonthDate.year, yearMonthDate.month, yearMonthDate.day, hourOfDay,
            minute, second
        )
        time = cal!!.timeInMillis
        isTimeSeted = true
    }

    override fun computeTime() {
        if (!isTimeSet && !isTimeSeted) {
            val cal = getInstance(Companion.timeZone)
            if (!isSet(HOUR_OF_DAY)) {
                super.set(HOUR_OF_DAY, cal[HOUR_OF_DAY])
            }
            if (!isSet(HOUR)) {
                super.set(HOUR, cal[HOUR])
            }
            if (!isSet(MINUTE)) {
                super.set(MINUTE, cal[MINUTE])
            }
            if (!isSet(SECOND)) {
                super.set(SECOND, cal[SECOND])
            }
            if (!isSet(MILLISECOND)) {
                super.set(MILLISECOND, cal[MILLISECOND])
            }
            if (!isSet(ZONE_OFFSET)) {
                super.set(ZONE_OFFSET, cal[ZONE_OFFSET])
            }
            if (!isSet(DST_OFFSET)) {
                super.set(DST_OFFSET, cal[DST_OFFSET])
            }
            if (!isSet(AM_PM)) {
                super.set(AM_PM, cal[AM_PM])
            }
            if (internalGet(HOUR_OF_DAY) >= 12 && internalGet(HOUR_OF_DAY) <= 23) {
                super.set(AM_PM, PM)
                super.set(HOUR, internalGet(HOUR_OF_DAY) - 12)
            } else {
                super.set(HOUR, internalGet(HOUR_OF_DAY))
                super.set(AM_PM, AM)
            }
            val yearMonthDate = jalaliToGregorian(
                YearMonthDate(
                    internalGet(YEAR), internalGet(
                        MONTH
                    ), internalGet(DAY_OF_MONTH)
                )
            )
            cal[yearMonthDate.year, yearMonthDate.month, yearMonthDate.day, internalGet(HOUR_OF_DAY), internalGet(
                MINUTE
            )] =
                internalGet(SECOND)
            time = cal.timeInMillis
        } else if (!isTimeSet && isTimeSeted) {
            if (internalGet(HOUR_OF_DAY) >= 12 && internalGet(HOUR_OF_DAY) <= 23) {
                super.set(AM_PM, PM)
                super.set(HOUR, internalGet(HOUR_OF_DAY) - 12)
            } else {
                super.set(HOUR, internalGet(HOUR_OF_DAY))
                super.set(AM_PM, AM)
            }
            cal = GregorianCalendar()
            super.set(ZONE_OFFSET, Companion.timeZone.rawOffset)
            super.set(DST_OFFSET, Companion.timeZone.dstSavings)
            val yearMonthDate = jalaliToGregorian(
                YearMonthDate(
                    internalGet(YEAR), internalGet(
                        MONTH
                    ), internalGet(DAY_OF_MONTH)
                )
            )
            cal!![yearMonthDate.year, yearMonthDate.month, yearMonthDate.day, internalGet(
                HOUR_OF_DAY
            ), internalGet(MINUTE)] = internalGet(SECOND)
            time = cal!!.timeInMillis
        }
    }

    override fun set(field: Int, value: Int) {
        when (field) {
            DATE -> {
                super.set(field, 0)
                add(field, value)
            }
            MONTH -> {
                if (value > 11) {
                    super.set(field, 11)
                    add(field, value - 11)
                } else if (value < 0) {
                    super.set(field, 0)
                    add(field, value)
                } else {
                    super.set(field, value)
                }
            }
            DAY_OF_YEAR -> {
                if (isSet(YEAR) && isSet(MONTH) && isSet(DAY_OF_MONTH)) {
                    super.set(YEAR, internalGet(YEAR))
                    super.set(MONTH, 0)
                    super.set(DATE, 0)
                    add(field, value)
                } else {
                    super.set(field, value)
                }
            }
            WEEK_OF_YEAR -> {
                if (isSet(YEAR) && isSet(MONTH) && isSet(DAY_OF_MONTH)) {
                    add(field, value - get(WEEK_OF_YEAR))
                } else {
                    super.set(field, value)
                }
            }
            WEEK_OF_MONTH -> {
                if (isSet(YEAR) && isSet(MONTH) && isSet(DAY_OF_MONTH)) {
                    add(field, value - get(WEEK_OF_MONTH))
                } else {
                    super.set(field, value)
                }
            }
            DAY_OF_WEEK -> {
                if (isSet(YEAR) && isSet(MONTH) && isSet(DAY_OF_MONTH)) {
                    add(DAY_OF_WEEK, value % 7 - get(DAY_OF_WEEK))
                } else {
                    super.set(field, value)
                }
            }
            HOUR_OF_DAY, HOUR, MINUTE, SECOND, MILLISECOND, ZONE_OFFSET, DST_OFFSET -> {
                if (isSet(YEAR) && isSet(MONTH) && isSet(DATE) && isSet(HOUR) && isSet(HOUR_OF_DAY) &&
                    isSet(MINUTE) && isSet(SECOND) && isSet(MILLISECOND)
                ) {
                    cal = GregorianCalendar()
                    var yearMonthDate = jalaliToGregorian(
                        YearMonthDate(
                            internalGet(YEAR), internalGet(
                                MONTH
                            ), internalGet(DATE)
                        )
                    )
                    cal!![yearMonthDate.year, yearMonthDate.month, yearMonthDate.day, internalGet(
                        HOUR_OF_DAY
                    ), internalGet(
                        MINUTE
                    )] = internalGet(SECOND)
                    cal!![field] = value
                    yearMonthDate =
                        gregorianToJalali(YearMonthDate(cal!![YEAR], cal!![MONTH], cal!![DATE]))
                    super.set(YEAR, yearMonthDate.year)
                    super.set(MONTH, yearMonthDate.month)
                    super.set(DATE, yearMonthDate.day)
                    super.set(HOUR_OF_DAY, cal!![HOUR_OF_DAY])
                    super.set(MINUTE, cal!![MINUTE])
                    super.set(SECOND, cal!![SECOND])
                } else {
                    super.set(field, value)
                }
            }
            else -> {
                super.set(field, value)
            }
        }
    }

    override fun computeFields() {
        val temp = isTimeSet
        if (!areFieldsSet) {
            minimalDaysInFirstWeek = 1
            firstDayOfWeek = SATURDAY

            //Day_Of_Year
            var dayOfYear = 0
            var index = 0
            while (index < fields[2]) {
                dayOfYear += jalaliDaysInMonth[index++]
            }
            dayOfYear += fields[5]
            super.set(DAY_OF_YEAR, dayOfYear)
            //***

            //Day_of_Week
            super.set(
                DAY_OF_WEEK, dayOfWeek(
                    jalaliToGregorian(
                        YearMonthDate(
                            fields[1], fields[2], fields[5]
                        )
                    )
                )
            )
            //***

            //Day_Of_Week_In_Month
            if (0 < fields[5] && fields[5] < 8) {
                super.set(DAY_OF_WEEK_IN_MONTH, 1)
            }
            if (7 < fields[5] && fields[5] < 15) {
                super.set(DAY_OF_WEEK_IN_MONTH, 2)
            }
            if (14 < fields[5] && fields[5] < 22) {
                super.set(DAY_OF_WEEK_IN_MONTH, 3)
            }
            if (21 < fields[5] && fields[5] < 29) {
                super.set(DAY_OF_WEEK_IN_MONTH, 4)
            }
            if (28 < fields[5] && fields[5] < 32) {
                super.set(DAY_OF_WEEK_IN_MONTH, 5)
            }
            //***

            //Week_Of_Year
            super.set(WEEK_OF_YEAR, weekOfYear(fields[6], fields[1]))
            //***

            //Week_Of_Month
            super.set(
                WEEK_OF_MONTH, weekOfYear(fields[6], fields[1]) - weekOfYear(
                    fields[6] - fields[5], fields[1]
                ) + 1
            )
            //
            isTimeSet = temp
        }
    }

    override fun add(field: Int, amount: Int) {
        var amount = amount
        if (field == MONTH) {
            amount += get(MONTH)
            add(YEAR, amount / 12)
            super.set(MONTH, amount % 12)
            if (get(DAY_OF_MONTH) > jalaliDaysInMonth[amount % 12]) {
                super.set(DAY_OF_MONTH, jalaliDaysInMonth[amount % 12])
                if (get(MONTH) == 11 && isLeepYear(get(YEAR))) {
                    super.set(DAY_OF_MONTH, 30)
                }
            }
            complete()
        } else if (field == YEAR) {
            super.set(YEAR, get(YEAR) + amount)
            if (get(DAY_OF_MONTH) == 30 && get(MONTH) == 11 && !isLeepYear(get(YEAR))) {
                super.set(DAY_OF_MONTH, 29)
            }
            complete()
        } else {
            var yearMonthDate = jalaliToGregorian(YearMonthDate(get(YEAR), get(MONTH), get(DATE)))
            val gc: Calendar = GregorianCalendar(
                yearMonthDate.year, yearMonthDate.month, yearMonthDate.day,
                get(HOUR_OF_DAY), get(MINUTE), get(SECOND)
            )
            gc.add(field, amount)
            yearMonthDate = gregorianToJalali(YearMonthDate(gc[YEAR], gc[MONTH], gc[DATE]))
            super.set(YEAR, yearMonthDate.year)
            super.set(MONTH, yearMonthDate.month)
            super.set(DATE, yearMonthDate.day)
            super.set(HOUR_OF_DAY, gc[HOUR_OF_DAY])
            super.set(MINUTE, gc[MINUTE])
            super.set(SECOND, gc[SECOND])
            complete()
        }
    }

    override fun roll(field: Int, up: Boolean) {
        roll(field, if (up) +1 else -1)
    }

    override fun roll(field: Int, amount: Int) {
        if (amount == 0) {
            return
        }
        require(!(field < 0 || field >= ZONE_OFFSET))
        complete()
        when (field) {
            AM_PM -> {
                if (amount % 2 != 0) {
                    if (internalGet(AM_PM) == AM) {
                        fields[AM_PM] = PM
                    } else {
                        fields[AM_PM] = AM
                    }
                    if (get(AM_PM) == AM) {
                        super.set(HOUR_OF_DAY, get(HOUR))
                    } else {
                        super.set(HOUR_OF_DAY, get(HOUR) + 12)
                    }
                }
            }
            YEAR -> {
                super.set(YEAR, internalGet(YEAR) + amount)
                if (internalGet(MONTH) == 11 && internalGet(DAY_OF_MONTH) == 30 && !isLeepYear(
                        internalGet(
                            YEAR
                        )
                    )
                ) {
                    super.set(DAY_OF_MONTH, 29)
                }
            }
            MINUTE -> {
                val unit = 60
                var m = (internalGet(MINUTE) + amount) % unit
                if (m < 0) {
                    m += unit
                }
                super.set(MINUTE, m)
            }
            SECOND -> {
                val unit = 60
                var s = (internalGet(SECOND) + amount) % unit
                if (s < 0) {
                    s += unit
                }
                super.set(SECOND, s)
            }
            MILLISECOND -> {
                val unit = 1000
                var ms = (internalGet(MILLISECOND) + amount) % unit
                if (ms < 0) {
                    ms += unit
                }
                super.set(MILLISECOND, ms)
            }
            HOUR -> {
                super.set(HOUR, (internalGet(HOUR) + amount) % 12)
                if (internalGet(HOUR) < 0) {
                    fields[HOUR] += 12
                }
                if (internalGet(AM_PM) == AM) {
                    super.set(HOUR_OF_DAY, internalGet(HOUR))
                } else {
                    super.set(HOUR_OF_DAY, internalGet(HOUR) + 12)
                }
            }
            HOUR_OF_DAY -> {
                run {
                    fields[HOUR_OF_DAY] = (internalGet(HOUR_OF_DAY) + amount) % 24
                    if (internalGet(HOUR_OF_DAY) < 0) {
                        fields[HOUR_OF_DAY] += 24
                    }
                    if (internalGet(HOUR_OF_DAY) < 12) {
                        fields[AM_PM] = AM
                        fields[HOUR] = internalGet(HOUR_OF_DAY)
                    } else {
                        fields[AM_PM] = PM
                        fields[HOUR] = internalGet(HOUR_OF_DAY) - 12
                    }
                }
                run {
                    var mon = (internalGet(MONTH) + amount) % 12
                    if (mon < 0) {
                        mon += 12
                    }
                    super.set(MONTH, mon)
                    var monthLen = jalaliDaysInMonth[mon]
                    if (internalGet(MONTH) == 11 && isLeepYear(internalGet(YEAR))) {
                        monthLen = 30
                    }
                    if (internalGet(DAY_OF_MONTH) > monthLen) {
                        super.set(DAY_OF_MONTH, monthLen)
                    }
                }
            }
            MONTH -> {
                var mon = (internalGet(MONTH) + amount) % 12
                if (mon < 0) {
                    mon += 12
                }
                super.set(MONTH, mon)
                var monthLen = jalaliDaysInMonth[mon]
                if (internalGet(MONTH) == 11 && isLeepYear(internalGet(YEAR))) {
                    monthLen = 30
                }
                if (internalGet(DAY_OF_MONTH) > monthLen) {
                    super.set(DAY_OF_MONTH, monthLen)
                }
            }
            DAY_OF_MONTH -> {
                var unit = 0
                if (0 <= get(MONTH) && get(MONTH) <= 5) {
                    unit = 31
                }
                if (6 <= get(MONTH) && get(MONTH) <= 10) {
                    unit = 30
                }
                if (get(MONTH) == 11) {
                    unit = if (isLeepYear(get(YEAR))) {
                        30
                    } else {
                        29
                    }
                }
                var d = (get(DAY_OF_MONTH) + amount) % unit
                if (d < 0) {
                    d += unit
                }
                super.set(DAY_OF_MONTH, d)
            }
            WEEK_OF_YEAR -> {}
            DAY_OF_YEAR -> {
                val unit = if (isLeepYear(internalGet(YEAR))) 366 else 365
                var dayOfYear = (internalGet(DAY_OF_YEAR) + amount) % unit
                dayOfYear = if (dayOfYear > 0) dayOfYear else dayOfYear + unit
                var month = 0
                var temp = 0
                while (dayOfYear > temp) {
                    temp += jalaliDaysInMonth[month++]
                }
                super.set(MONTH, --month)
                super.set(DAY_OF_MONTH, jalaliDaysInMonth[internalGet(MONTH)] - (temp - dayOfYear))
            }
            DAY_OF_WEEK -> {
                var index = amount % 7
                if (index < 0) {
                    index += 7
                }
                var i = 0
                while (i != index) {
                    if (internalGet(DAY_OF_WEEK) == FRIDAY) {
                        add(DAY_OF_MONTH, -6)
                    } else {
                        add(DAY_OF_MONTH, +1)
                    }
                    i++
                }
            }
            else -> throw IllegalArgumentException()
        }
    }

    override fun getMinimum(field: Int): Int {
        return MIN_VALUES[field]
    }

    override fun getMaximum(field: Int): Int {
        return MAX_VALUES[field]
    }

    override fun getGreatestMinimum(field: Int): Int {
        return MIN_VALUES[field]
    }

    override fun getLeastMaximum(field: Int): Int {
        return LEAST_MAX_VALUES[field]
    }

    class YearMonthDate(var year: Int, var month: Int, var day: Int) {
        override fun toString(): String {
            return "$year/$month/$day"
        }
    }

    companion object {
        var gregorianDaysInMonth = intArrayOf(
            31, 28, 31, 30, 31,
            30, 31, 31, 30, 31, 30, 31
        )
        var jalaliDaysInMonth = intArrayOf(
            31, 31, 31, 31, 31, 31,
            30, 30, 30, 30, 30, 29
        )
        const val FARVARDIN = 0
        const val ORDIBEHESHT = 1
        const val KHORDAD = 2
        const val TIR = 3
        const val MORDAD = 4
        const val SHAHRIVAR = 5
        const val MEHR = 6
        const val ABAN = 7
        const val AZAR = 8
        const val DEY = 9
        const val BAHMAN = 10
        const val ESFAND = 11
        private var timeZone = TimeZone.getDefault()
        private var isTimeSeted = false
        private const val ONE_SECOND = 1000
        private const val ONE_MINUTE = 60 * ONE_SECOND
        private const val ONE_HOUR = 60 * ONE_MINUTE
        private const val ONE_DAY = (24 * ONE_HOUR).toLong()
        const val BCE = 0
        const val CE = 1
        const val AD = 1
        val MIN_VALUES = intArrayOf(
            BCE,  // ERA
            1,  // YEAR
            FARVARDIN,  // MONTH
            1,  // WEEK_OF_YEAR
            0,  // WEEK_OF_MONTH
            1,  // DAY_OF_MONTH
            1,  // DAY_OF_YEAR
            SATURDAY,  // DAY_OF_WEEK
            1,  // DAY_OF_WEEK_IN_MONTH
            AM,  // AM_PM
            0,  // HOUR
            0,  // HOUR_OF_DAY
            0,  // MINUTE
            0,  // SECOND
            0,  // MILLISECOND
            -13 * ONE_HOUR,  // ZONE_OFFSET (UNIX compatibility)
            0 // DST_OFFSET
        )
        val LEAST_MAX_VALUES = intArrayOf(
            CE,  // ERA
            292269054,  // YEAR
            ESFAND,  // MONTH
            52,  // WEEK_OF_YEAR
            4,  // WEEK_OF_MONTH
            28,  // DAY_OF_MONTH
            365,  // DAY_OF_YEAR
            FRIDAY,  // DAY_OF_WEEK
            4,  // DAY_OF_WEEK_IN
            PM,  // AM_PM
            11,  // HOUR
            23,  // HOUR_OF_DAY
            59,  // MINUTE
            59,  // SECOND
            999,  // MILLISECOND
            14 * ONE_HOUR,  // ZONE_OFFSET
            20 * ONE_MINUTE // DST_OFFSET (historical least maximum)
        )
        val MAX_VALUES = intArrayOf(
            CE,  // ERA
            292278994,  // YEAR
            ESFAND,  // MONTH
            53,  // WEEK_OF_YEAR
            6,  // WEEK_OF_MONTH
            31,  // DAY_OF_MONTH
            366,  // DAY_OF_YEAR
            FRIDAY,  // DAY_OF_WEEK
            6,  // DAY_OF_WEEK_IN
            PM,  // AM_PM
            11,  // HOUR
            23,  // HOUR_OF_DAY
            59,  // MINUTE
            59,  // SECOND
            999,  // MILLISECOND
            14 * ONE_HOUR,  // ZONE_OFFSET
            2 * ONE_HOUR // DST_OFFSET (double summer time)
        )

        fun gregorianToJalali(gregorian: YearMonthDate): YearMonthDate {
            require(!(gregorian.month > 11 || gregorian.month < -11))
            var jalaliYear: Int
            val jalaliMonth: Int
            val jalaliDay: Int
            var gregorianDayNo: Int
            var jalaliDayNo: Int
            val jalaliNP: Int
            var i: Int
            gregorian.year = gregorian.year - 1600
            gregorian.day = gregorian.day - 1
            gregorianDayNo =
                (365 * gregorian.year + Math.floor(((gregorian.year + 3) / 4).toDouble())
                    .toInt()
                        - Math.floor(((gregorian.year + 99) / 100).toDouble()).toInt()
                        + Math.floor(((gregorian.year + 399) / 400).toDouble()).toInt())
            i = 0
            while (i < gregorian.month) {
                gregorianDayNo += gregorianDaysInMonth[i]
                ++i
            }
            if (gregorian.month > 1 && (gregorian.year % 4 == 0 && gregorian.year % 100 != 0
                        || gregorian.year % 400 == 0)
            ) {
                ++gregorianDayNo
            }
            gregorianDayNo += gregorian.day
            jalaliDayNo = gregorianDayNo - 79
            jalaliNP = Math.floor((jalaliDayNo / 12053).toDouble()).toInt()
            jalaliDayNo = jalaliDayNo % 12053
            jalaliYear = 979 + 33 * jalaliNP + 4 * (jalaliDayNo / 1461)
            jalaliDayNo = jalaliDayNo % 1461
            if (jalaliDayNo >= 366) {
                jalaliYear += Math.floor(((jalaliDayNo - 1) / 365).toDouble()).toInt()
                jalaliDayNo = (jalaliDayNo - 1) % 365
            }
            i = 0
            while (i < 11 && jalaliDayNo >= jalaliDaysInMonth[i]) {
                jalaliDayNo -= jalaliDaysInMonth[i]
                ++i
            }
            jalaliMonth = i
            jalaliDay = jalaliDayNo + 1
            return YearMonthDate(jalaliYear, jalaliMonth, jalaliDay)
        }

        fun jalaliToGregorian(jalali: YearMonthDate): YearMonthDate {
            require(!(jalali.month > 11 || jalali.month < -11))
            var gregorianYear: Int
            val gregorianMonth: Int
            val gregorianDay: Int
            var gregorianDayNo: Int
            var jalaliDayNo: Int
            var leap: Int
            var i: Int
            jalali.year = jalali.year - 979
            jalali.day = jalali.day - 1
            jalaliDayNo =
                365 * jalali.year + (jalali.year / 33) * 8 + Math.floor(((jalali.year % 33 + 3) / 4).toDouble())
                    .toInt()
            i = 0
            while (i < jalali.month) {
                jalaliDayNo += jalaliDaysInMonth[i]
                ++i
            }
            jalaliDayNo += jalali.day
            gregorianDayNo = jalaliDayNo + 79
            gregorianYear = 1600 + 400 * Math.floor((gregorianDayNo / 146097).toDouble())
                .toInt() /* 146097 = 365*400 + 400/4 - 400/100 + 400/400 */
            gregorianDayNo = gregorianDayNo % 146097
            leap = 1
            if (gregorianDayNo >= 36525) /* 36525 = 365*100 + 100/4 */ {
                gregorianDayNo--
                gregorianYear += 100 * Math.floor((gregorianDayNo / 36524).toDouble())
                    .toInt() /* 36524 = 365*100 + 100/4 - 100/100 */
                gregorianDayNo = gregorianDayNo % 36524
                if (gregorianDayNo >= 365) {
                    gregorianDayNo++
                } else {
                    leap = 0
                }
            }
            gregorianYear += 4 * Math.floor((gregorianDayNo / 1461).toDouble())
                .toInt() /* 1461 = 365*4 + 4/4 */
            gregorianDayNo = gregorianDayNo % 1461
            if (gregorianDayNo >= 366) {
                leap = 0
                gregorianDayNo--
                gregorianYear += Math.floor((gregorianDayNo / 365).toDouble()).toInt()
                gregorianDayNo = gregorianDayNo % 365
            }
            i = 0
            while (gregorianDayNo >= gregorianDaysInMonth[i] + if (i == 1 && leap == 1) i else 0) {
                gregorianDayNo -= gregorianDaysInMonth[i] + if (i == 1 && leap == 1) i else 0
                i++
            }
            gregorianMonth = i
            gregorianDay = gregorianDayNo + 1
            return YearMonthDate(gregorianYear, gregorianMonth, gregorianDay)
        }

        fun weekOfYear(dayOfYear: Int, year: Int): Int {
            var dayOfYear = dayOfYear
            when (dayOfWeek(jalaliToGregorian(YearMonthDate(year, 0, 1)))) {
                2 -> dayOfYear++
                3 -> dayOfYear += 2
                4 -> dayOfYear += 3
                5 -> dayOfYear += 4
                6 -> dayOfYear += 5
                7 -> dayOfYear--
            }
            dayOfYear = Math.floor((dayOfYear / 7).toDouble()).toInt()
            return dayOfYear + 1
        }

        fun dayOfWeek(yearMonthDate: YearMonthDate): Int {
            val cal: Calendar =
                GregorianCalendar(yearMonthDate.year, yearMonthDate.month, yearMonthDate.day)
            return cal[DAY_OF_WEEK]
        }

        fun isLeepYear(year: Int): Boolean {
            //Algorithm from www.wikipedia.com
            return if (year % 33 == 1 || year % 33 == 5 || year % 33 == 9 || year % 33 == 13 || year % 33 == 17 || year % 33 == 22 || year % 33 == 26 || year % 33 == 30
            ) {
                true
            } else false
        }
    }
}