package pro.horoshilov.family.repository.specification;

abstract class ContactInformationSpecification implements ISqlSpecification{
    //language=sql
    final static String SQL =
            "select " +
            "       ci.contact_information_id, " +
            "       ci.person_id, " +
            "       ci.code, " +
            "       ci.value, " +
            "       ci.type, " +
            "       ci.position " +
            "from " +
            "      contact_information ci ";
}
