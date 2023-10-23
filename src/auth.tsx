
export default class Authentication{
    private authentication;
    constructor(){
        this.authentication = false;
    }

    setAuthentication(x:boolean){
        this.authentication = x;
    }

    getAuthentication(){
        return this.authentication;
    }

    

    login(user:string, pwd:string){

        try{
            if(user === "teste" && pwd ==="teste"){
                this.setAuthentication(true)
                console.log("loged in")
            }
            else{
                this.setAuthentication(false)
                console.log("Username or password are wrong.")
            }
        }
        catch(e){
            console.log(e)

        }
    }

    logout(){
        this.setAuthentication(false)
    }
}
