package wikipedia;

public @interface JsonInclude {
    public Include value() default Include.ALWAYS;//inclusion rules
    public Include content() default Include.ALWAYS;//rules on content (e.g. Map's elements)
    public Class<?> valueFilter() default Void.class;//used for values when Include.CUSTOM
    public Class<?> contentFilter() default Void.class;//used for contents values when Include.CUSTOM

    public enum Include{
        ALWAYS,//always include property
        NON_NULL,//do not include property with null value
        NON_ABSENT,//no null values including no content null values like Optional, AtomicReference etc
        NON_EMPTY,//NON_NULL + NON_ABSENT + values like empty Collections/Map/arrays/String etc are excluded
        NON_DEFAULT,//no default values, e.g. no primitives with default values
        CUSTOM,// a custom filter for exclusion specified by JsonInclude.valueFilter()
        USE_DEFAULTS//use defaults either from class level or ObjectMapper level
        ;
    }


}
