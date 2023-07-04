import {useEffect, useState} from "react";
import axios from "axios";
import {Theme} from "../utils/types.ts";
import DisplayTheme from "./DisplayTheme.tsx";
import AddTheme from "./AddTheme.tsx";

function Home() {

    const [themes, setThemes] = useState<Theme[]>([]);

    function fetchThemes() {
        axios.get("/api/theme")
            .then((res) => res.data)
            .catch((err) => {
                console.error(err);
            })
            .then((data) => {
                setThemes(data);
            });
    }

    useEffect(fetchThemes, []);

    return (
        <>
            {themes.map(theme => <DisplayTheme key={theme.id} theme={theme} setThemes={setThemes}/>)}
            <AddTheme onModifyThemes={setThemes}/>
        </>
    );
}

export default Home;
