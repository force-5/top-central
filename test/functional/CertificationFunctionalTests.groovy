import com.force5solutions.care.cc.Certification

class CertificationFunctionalTests extends CareFunctionalTests {

    void testCreateCertification() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        login(loginFormData)
        get('/certification/create')
        int initialCount = Certification.count()
        CertificationFormData certificationData = CertificationFormData.getDefaultCertificationFormData()
        createCertification(certificationData)
        TARGET_ARGS_WITH_VALUES = ["${certificationData.name}"]
        assertContentContains getMessage('certification.create.message', TARGET_ARGS_WITH_VALUES)
        assertContentContains "${certificationData.name}"
        assertContentContains "${certificationData.description}"
        assertContentContains "${certificationData.period}"
        int finalCount = Certification.count()
        assertEquals(1, finalCount - initialCount)
        logout()
    }

    void testCreateCertification_BLANK_NAME() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        login(loginFormData)
        get('/certification/create')
        int initialCount = Certification.count()
        CertificationFormData certificationData = CertificationFormData.getDefaultCertificationFormData()
        certificationData.name = ""
        createCertification(certificationData)
        assertContentContains getMessage('certification.invalid.values.message')
        int finalCount = Certification.count()
        assertEquals(0, finalCount - initialCount)
        logout()
    }

    void testEditCertification() {
        LoginFormData loginFormData = LoginFormData.getDefaultLoginFormData()
        login(loginFormData)
        get('/certification/create')
        int initialCount = Certification.count()
        CertificationFormData certificationData = CertificationFormData.getDefaultCertificationFormData()
        createCertification(certificationData)
        TARGET_ARGS_WITH_VALUES = ["${certificationData.name}"]
        assertContentContains getMessage('certification.create.message', TARGET_ARGS_WITH_VALUES)
        assertContentContains "${certificationData.name}"
        assertContentContains "${certificationData.description}"
        assertContentContains "${certificationData.period}"
        int intermediateCount = Certification.count()
        assertEquals(1, intermediateCount - initialCount)

        Certification certification = Certification.findByName(certificationData.name)
        get("/certification/edit/${certification.id}")
        certificationData.name = "New Edited Certification-${System.currentTimeMillis()}"
        updateCertification(certificationData)
        int finalCount = Certification.count();
        assertEquals(0, finalCount - intermediateCount)
        TARGET_ARGS_WITH_VALUES = ["${certificationData.name}"]
        assertContentContains getMessage('certification.update.message', TARGET_ARGS_WITH_VALUES)
        logout()
    }
}
