import _ from 'lodash'
export default{
    data() {
        return{
            patients: [],
            doctors: [],
        }
    },
    methods:{
       setPatients(patients){
           this.patients = patients;
       },
       getPatients(){
           return this.patients;
       },
       setDoctors(doctors){
        this.doctors = doctors;
       },
       getDoctors(){
         return this.doctors;
       },
    }
}