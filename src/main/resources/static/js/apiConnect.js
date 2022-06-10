
console.log("apiConnect log................")

const serverHost = "http://localhost:8080"

async function getTokens(mid, mpw) {

    const response = await axios.post(`${serverHost}/generateToken`, {mid,mpw})

    const data = response.data

    console.log(data)


    return data
}

async function getServerData(path, data){

    const accessToken = localStorage.getItem("access")

    console.log("accessToken", accessToken)

    if(!accessToken){
        throw {ERROR:'Access Token is null'}
    }

    const authHeader = {"Authorization": `Bearer ${accessToken}`}
    try{
        const response = await axios.get(`${serverHost}${path}`,
            {params: data, headers:authHeader})

        return response.data
    }catch(err){
        throw err.response.data
    }
}