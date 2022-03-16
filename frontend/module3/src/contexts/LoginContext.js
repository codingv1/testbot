import React, { createContext, useState } from 'react';
import axios from 'axios';
import { API_URL } from '../Constants';
import { useHistory } from 'react-router-dom';
// creating login context to store the values.
export const LoginContext = createContext();


function LoginContextProvider(props) {
    let history = useHistory();
    

    const defaultUser = {
        ccNumber: 0,
        ccName: "",
        userName: "",
        userId: "",
        token: "",
        availableRedeemPoints: 0,
        totalRewardsGained: 0
    }
    const [loggedInUser, setLoggedInUser] = useState(defaultUser)
    const [ isLoggedIn, setIsLoggedIn] = useState(false);

    // const setToken = (token) =>{
    //     let user = Object.assign({},loggedInUser,{token:token})
    //     setLoggedInUser( user);
    // } 

    const setLoginUserDetails = (user) =>  {

        console.log("setting log in user details");
        setIsLoggedIn(true)
        
        setLoggedInUser( user);
        
        
        
    }

    const logoutUser = async (e) =>{


        let logoutDetail = {
            userId: loggedInUser.userId,
            token: loggedInUser.token
        }
        await axios.post(API_URL + 'ccuser/ccuser/logout', logoutDetail).then(response => {
                console.log("logging out");
            
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response);
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('error', error.message);

                }
                // setUserId('');
                // setPassword('');
            })

        history.push('/login');
        setIsLoggedIn(false);
        setLoggedInUser(defaultUser);


    } 

    const refreshLoginDetails = async () => {

        let loginDetail = {
            userId: loggedInUser.userId,
            password: loggedInUser.password
        }
        await axios.post(API_URL + 'ccuser/ccuser/login', loginDetail)
        .then(response => {
            console.log(response);
            
            // updating the login context
            setLoginUserDetails(response.data.body)
           

        })
        .catch(error => {
            if (error.response) {
                console.log(error.response);
            } else if (error.request) {
                console.log(error.request);
            } else {
                console.log('error', error.message);

            }
        

        })

    }

    return (
        <div>
            <LoginContext.Provider
                value={{ loggedInUser, 
                         isLoggedIn, 
                         setLoginUserDetails, 
                         logoutUser,
                         refreshLoginDetails
                        }}
            >
                {props.children}
            </LoginContext.Provider>
        </div>
    );

}

export default LoginContextProvider;