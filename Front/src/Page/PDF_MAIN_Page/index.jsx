import React,{useState,useEffect} from "react";
import "./styles.css"
import PDFmaincompoent from "../../components/Pdf_main"
import { useSelector } from "react-redux";
import dog from "../../speekdoc.png"
import { Link } from "react-router-dom";

const PDFMAINPAGE =() =>{
    

    const {name,date} = useSelector(state=>state);
    const [pdflist,setPdflist] = useState([])
    console.log(name);

    const datas = [
        {id: 1,date:"2023-05-03",name:"웹서버"},
        {id: 2,date:"2023-05-04",name:"연구 및 프로젝트"},
        {id: 3,date:"2023-05-01",name:"낙서"},
        {id: 4,date:"2023-03-02",name:"서류문서"},
        {id: 6,date:"2023-03-02",name:"테스트"},
        {id: 7,date:"2023-03-02",name:"테스트2"},        
    ]
    

    useEffect(()=>{
        setPdflist(datas);
    },[])
    useEffect(()=>{
        let copy = [...pdflist];
        copy.push({id:1,date:date,name:name})
        setPdflist(copy);
        i++;
    },[name])
    console.log(pdflist);
    
 var i=0;
    return(
        <div className="Main">
            {
            pdflist.map((e)=>{
                    console.log(e);
                    console.log(i);
                    // i++;
                    return(
                        <PDFmaincompoent image={"image/image"+{i}+".png"}content={e.date}pdfname={e.name} id={i=i+1}/>        
                        
                        )         
                                   
                })            
            }
             <div className="active1">                
                <img src={dog} alt="" />                 
            </div>       
        </div>
    )
}

export default PDFMAINPAGE
 