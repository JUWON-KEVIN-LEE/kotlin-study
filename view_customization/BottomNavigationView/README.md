# Bottom Navigation View
### 0. 

<br>

### 1.

**0. Init**

    class BottomNavigationView : FrameLayout {
    	...
    	constructor(context: Context) : super(context) {
            init(context, null)
        }
    
        constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
            init(context, attrs)
        }
    
        constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
            init(context, attrs)
        }
    }

**1. Set container layout on the view**

```kotlin
 /**
 * set item container on the bottom navigation layout
 */
private fun setContainer() {
    container = LinearLayout(con)
    container!!.orientation = LinearLayout.HORIZONTAL
    container!!.gravity = Gravity.CENTER

    val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, layoutHeight.toInt())
    addView(container, layoutParams)
}
```


**2. Add Item View(s) on the container layout**


```kotlin
private fun addItemViews() {
  	val inflater = con!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    val height = res!!.getDimension(R.dimen.bottom_navigation_height)

	val width = width // layout width
    val minWidth = res!!.getDimension(R.dimen.bottom_navigation_item_min_width)
    val maxWidth = res!!.getDimension(R.dimen.bottom_navigation_item_max_width)

    Log.d(TAG, "width : " + width)

    if(parent !is ConstraintLayout)
        if(width == 0 || items.size == 0) return

	var itemWidth = (width / items.size).toFloat()

	if(itemWidth < minWidth) itemWidth = minWidth
    else if(itemWidth > maxWidth) itemWidth = maxWidth

    var activeTextSize = res!!.getDimension(R.dimen.bottom_navigation_active_text_size)
    var inactiveTextSize = res!!.getDimension(R.dimen.bottom_navigation_inactive_text_size)

    if(textActiveSize > 0) activeTextSize = textActiveSize
    if(textInactiveSize > 0) inactiveTextSize = textInactiveSize

    Log.d(TAG, "ats : $activeTextSize, iats : $inactiveTextSize")
  
    //TODO text color

    for(item in items) {
        val index = items.indexOf(item)
        val view = inflater.inflate(R.layout.bottom_navigation_item, this, false)

        view.icon.setImageResource(item.drawableRes)
        // view.icon.setColorFilter(item.colorRes)

        view.title.text = item.title
        textTypeface?.let { view.title.typeface = textTypeface }

        view.setOnClickListener{updateItem(index)}

        if(index == currentItem) {
            view.isSelected = true

            view.icon.setColorFilter(activeColor)

            view.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, activeTextSize)
            view.title.setTextColor(activeColor)
        } else {
            view.isSelected = false

            view.icon.setColorFilter(inactiveColor)

            view.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, inactiveTextSize)
            view.title.setTextColor(inactiveColor)
        }

        val params = LayoutParams(itemWidth.toInt(), height.toInt())
        container!!.addView(view, params)
        views.add(view)
    }
}
```
<br>

### 2.

<br>

### 3.

<br>

### 4.