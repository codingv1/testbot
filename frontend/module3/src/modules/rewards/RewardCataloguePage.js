
import React, { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { API_URL } from '../../Constants';
import CatalogueItem from './CatalogueItem';
import LoginPage from '../login/LoginPage';
import { LoginContext } from '../../contexts/LoginContext';


function RewardCataloguePage() {



    const [catalogueList, setCatalogueList] = useState([]);
    const [catalogueListLoaded, setCatalogueListLoaded] = useState(false);

    const { loggedInUser } = useContext(LoginContext);

    const fetchCatalogueList = async () => {

        console.log("token is:"+loggedInUser.token)
        setCatalogueListLoaded(false);


        await axios.get(API_URL + 'catalogue/catalogue/?token='+loggedInUser.token)
            .then(response => {
                console.log(response);
                setCatalogueList(response.data.body);
                setCatalogueListLoaded(true);
            })
            //if error/500, switch to login page
            .catch(error => {
                if (error.response) {
                    console.log(error.response);
                    setCatalogueListLoaded(true);

                } else if (error.request) {
                    console.log(error.request);
                    setCatalogueListLoaded(true);

                }
            })
    }

    useEffect(() => {
        fetchCatalogueList()
    }, []);


    return (
        <div className="reward-catalogue">
            <h3><i className="fas fa-gift" style={{color:'green'}}></i>&nbsp;Welcome to Rewards Catalogue </h3>

            <hr></hr>
            <div className="reward-catalogue">

                {
                    catalogueListLoaded ?
                        (
                            <div className="row" >
                                {catalogueList.map(c => (

                                    <div className="col-md-4" key={c.id}>
                                        <CatalogueItem catalogue={c} />
                                        
                                        <br></br>
                                    </div>


                                ))
                                }
                            </div>
                        ) :
                        (
                            <div className="loading-list">
                                <h5>Loading catalogue items... </h5>
                            </div>
                        )
                }


            </div>

        </div>
    );
            
            
}

export default RewardCataloguePage;