package pro.horoshilov.family.repository.specification;

abstract class PersonSpecification {
    //language=sql
    final static String SQL =
            "select " +
            "      p.person_id, " +
            "      p.birthday, " +
            "      p.death, " +
            "      p.avatar_id, " +
            "      p.description, " +
            "      p.name_first, " +
            "      p.name_last, " +
            "      p.name_middle, " +
            "      p.person_id, " +
            "      p.sex " +
            "from " +
            "      person p ";
}
